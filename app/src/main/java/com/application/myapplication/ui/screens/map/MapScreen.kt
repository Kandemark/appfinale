package com.application.myapplication.ui.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapScreen(navController: NavHostController) {
    val localContext = LocalContext.current
    val mapView = remember { MapView(localContext) }
    var locationPermissionGranted by remember { mutableStateOf(false) }
    val currentPermissionState by rememberUpdatedState(locationPermissionGranted)

    // Location permission request launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        locationPermissionGranted = isGranted
    }

    // Request location permission
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                localContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    DisposableEffect(mapView) {
        mapView.onCreate(Bundle())
        mapView.onResume()

        onDispose {
            mapView.onPause()
            mapView.onDestroy()
        }
    }

    AndroidView(
        factory = {
            mapView.apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = { updatedMapView ->
            updatedMapView.getMapAsync { googleMap ->
                try {
                    setupMap(googleMap, localContext, currentPermissionState)
                } catch (e: Exception) {
                    Log.e("MapScreen", "Error in getMapAsync: ${e.localizedMessage}")
                }
            }
        }
    )
}

private fun setupMap(googleMap: GoogleMap, context: android.content.Context, locationPermissionGranted: Boolean) {
    try {
        if (locationPermissionGranted) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = true
            }
        }

        // Enable traffic layer
        googleMap.isTrafficEnabled = true

        // Get the device's current location and set the camera position
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                googleMap.addMarker(
                    MarkerOptions().position(currentLatLng).title("You are here")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            } ?: run {
                Log.e("MapScreen", "Location is null")
            }
        }.addOnFailureListener { e ->
            Log.e("MapScreen", "Error fetching location: ${e.localizedMessage}")
        }

        // Default location if permission is not granted or location is null
        if (!locationPermissionGranted) {
            val nairobi = LatLng(-1.2921, 36.8219)
            googleMap.addMarker(
                MarkerOptions().position(nairobi).title("Nairobi")
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nairobi, 12f))
        }

    } catch (e: SecurityException) {
        Log.e("MapScreen", "SecurityException: ${e.localizedMessage}")
    }
}

