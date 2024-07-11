package com.application.myapplication.navigation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.application.myapplication.ui.screens.login.LoginScreen
import com.application.myapplication.ui.screens.map.MapScreen
import com.application.myapplication.ui.screens.schedule.ScheduleScreen
import com.application.myapplication.ui.screens.payment.PaymentScreen
import com.application.myapplication.ui.screens.profile.ProfileScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.application.myapplication.ui.screens.notification.NotificationScreen
import kotlinx.coroutines.launch
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.application.myapplication.ui.screens.dashboard.DashboardScreen
import com.application.myapplication.ui.screens.forgetpassword.ForgetPasswordScreen
import com.application.myapplication.ui.screens.login.LoginSuccessScreen
import com.application.myapplication.ui.screens.notification.MainScreen
import com.application.myapplication.ui.screens.onboarding.SplashScreen
import com.application.myapplication.ui.screens.otp.OTPScreen
import com.application.myapplication.ui.screens.signup.SignUpScreen




@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBadgeCountChange: (Int) -> Unit
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = SignIn.route,
        modifier = modifier
    ) {
        // Define your composable destinations here
        composable(route = SignIn.route) {
            LoginScreen(navController = navController)
        }

        composable(route = SignUp.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = ForgotPass.route) {
            ForgetPasswordScreen(navController = navController)
        }

        composable(route = Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        composable(route = Screen.Notification.route) {
            NotificationScreen(onBadgeCountChange = onBadgeCountChange)
        }

        composable(route = Screen.Schedule.route) {
            ScheduleScreen(navController = navController)
        }

        composable(route = Otp.route) {
            OTPScreen(navController = navController)
        }

        composable(Profile.route) {
            ProfileScreen(
                navController = navController,
                logout = {
                    navController.navigate(SignIn.route) {
                        popUpTo(SignIn.route) {
                            inclusive = true
                        }
                    }
                    Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show()
                },
                onBackBtnClick = { navController.popBackStack() }
            )
        }

        composable(Payment.route) {
            PaymentScreen(navController = navController)
        }
    }
}








































//
//@Composable
//fun AppNavigation(navController: NavHostController) {
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    val topBarVisibilityState = remember {
//        mutableStateOf(true)
//    }
//
//    BackHandler {
//        if (drawerState.isOpen) {
//            scope.launch { drawerState.close() }
//        } else {
//            navController.popBackStack()
//        }
//    }
//
//    ModalDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            DrawerContent(navController, drawerState)
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                AppBar(
//                    navController = navController,
//                    isVisible = topBarVisibilityState.value,
//                    searchCharSequence = {
//
//                    },
//                    onCartIconClick = {
//                        scope.launch {
//                            if (drawerState.isClosed) drawerState.open()
//                            else drawerState.close()
//                        }
//                    },
//                    onNotificationIconClick = {
//                        navController.navigate("notification")
//                    }
//                )
//            },
//            bottomBar = { BottomNavBar(navController) }
//        ) { innerPadding ->
//            NavHost(navController, startDestination = "dashboard", Modifier.padding(innerPadding)) {
//                composable("login") { LoginScreen(navController) }
//                composable(Profile.route) {
//                    ProfileScreen(
//                        navController = navController,
//                        logout = {
//                            // Perform logout action here
//                        },
//                        onBackBtnClick = {
//                            navController.popBackStack()
//                        }
//
//                    )
//                }
//                composable("notification") { NotificationScreen(onBadgeCountChange = onBadgeCountChange) }
//                composable("map") { MapScreen(navController) }
//                composable("dashboard") { DashboardScreen(navController) }
//                composable("schedule") { ScheduleScreen(navController) }
//                composable("payment") { PaymentScreen(navController) }
//                composable("signup") { SignUpScreen(navController) }
//                composable("login success") { LoginSuccessScreen(navController) }
//                composable("forget password") { ForgetPasswordScreen(navController) }
//                composable("onboarding") { SplashScreen(navController) }
//                composable("OtpScreen") { OTPScreen(navController) }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun DrawerContent(navController: NavController, drawerState: DrawerState) {
//
//    val scope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        DrawerItem("Dashboard", Icons.Default.Home, "dashboard", navController, drawerState)
//        DrawerItem("Profile", Icons.Default.Person, "profile", navController, drawerState)
//        DrawerItem("Notifications", Icons.Default.Notifications, "notification", navController, drawerState)
//        DrawerItem("Map", Icons.Default.List, "map", navController, drawerState)
////        DrawerItem("Schedule", Icons.Default.Schedule, "schedule", navController, drawerState)
////        DrawerItem("Payment", Icons.Default.Payment, "payment", navController, drawerState)
////        DrawerItem("Splash", Icons.Default.SplashScreen, "splash", navController, drawerState)
//        DrawerItem("Login", Icons.Default.Check, "login", navController, drawerState)
//    }
//}
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun DrawerItem(
//    text: String,
//    icon: ImageVector,
//    destination: String,
//    navController: NavController,
//    drawerState: DrawerState
//) {
//    val scope = rememberCoroutineScope()
//    ListItem(
//        icon = { Icon(imageVector = icon, contentDescription = null) },
//        text = { Text(text) },
//        modifier = Modifier.clickable {
//            navController.navigate(destination) {
//                popUpTo(navController.graph.startDestinationId) {
//                    saveState = true
//                }
//                launchSingleTop = true
//                restoreState = true
//            }
//            scope.launch { drawerState.close() }
//        }
//    )
//}
//
//














