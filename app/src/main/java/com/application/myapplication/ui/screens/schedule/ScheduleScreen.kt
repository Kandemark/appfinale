package com.application.myapplication.ui.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.myapplication.R
import com.application.myapplication.navigation.Screen
import com.application.myapplication.ui.theme.PrimaryColor
import com.application.myapplication.ui.theme.PrimaryLightColor
import com.application.myapplication.ui.theme.TextColor


data class Route(
    val id: Int,
    var routeName: String,
    var startPoint: String,
    var endPoint: String
)

@Composable
fun ScheduleScreen(
    navController: NavController,
    routeState: LazyListState = rememberLazyListState(),
    scheduleState: LazyListState = rememberLazyListState(),
    passengerState: LazyListState = rememberLazyListState(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
    ) {

        // Route Management
        SectionHeader(title = "Current Route")
        LazyColumn(state = routeState) {
            item {
                RouteCard(routeName = "Route 1", startPoint = "Station A", endPoint = "Station B")
            }
            item {
                RouteCard(routeName = "Route 2", startPoint = "Station C", endPoint = "Station D")
            }
        }

        Spacer(modifier = Modifier.height(15.dp))



        // Schedule Management
        SectionHeader(title = "Schedule")
        LazyColumn(state = scheduleState) {
            item {
                ScheduleCard(time = "08:00 AM", status = "On Time")
            }
            item {
                ScheduleCard(time = "10:00 AM", status = "Delayed")
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Passenger List
        SectionHeader(title = "Passengers")
        LazyColumn(state = passengerState) {
            item {
                PassengerCard(name = "John Doe", status = "Checked In")
            }
            item {
                PassengerCard(name = "Jane Smith", status = "Not Checked In")
            }
        }
    }
}


//@Composable
//fun RouteManagement(
//    routeState: LazyListState
//) {
//    // Sample data for routes (replace with your actual data management)
//    val routes = remember {
//        mutableStateListOf(
//            Route(id = 1, routeName = "Route 1", startPoint = "Station A", endPoint = "Station B"),
//            Route(id = 2, routeName = "Route 2", startPoint = "Station C", endPoint = "Station D")
//        )
//    }
//
//    LazyColumn(state = routeState) {
//        items(routes.size) { index ->
//            val route = routes[index]
//
//            // Display RouteCard for each route
//            RouteCard(
//                routeName = route.routeName,
//                startPoint = route.startPoint,
//                endPoint = route.endPoint,
//                onClick = {
//                    // Handle click to update route (example: navigate to update screen)
//                    // For simplicity, this example just updates the route name
//                    routes[index].routeName = "Updated Route ${route.id}"
//                }
//            )
//        }
//
//        // Add a button at the end to add new routes
//        item {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Button(
//                    onClick = {
//                        // Add new route (example: for demo purposes)
//                        routes.add(Route(id = routes.size + 1, routeName = "New Route", startPoint = "", endPoint = ""))
//                    }
//                ) {
//                    Text("Add New Route")
//                }
//            }
//        }
//    }
//}


@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryColor,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun RouteCard(
    routeName: String,
    startPoint: String,
    endPoint: String,
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(text = "Route Name: $routeName", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Start Point: $startPoint")
            Text(text = "End Point: $endPoint")
        }
    }
}


@Composable
fun ScheduleCard(time: String, status: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = PrimaryLightColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { }
    ) {
        Row(modifier = Modifier.padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = time, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = status, color = if (status == "On Time") Color.Green else Color.Red)
        }
    }
}

@Composable
fun PassengerCard(name: String, status: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = PrimaryLightColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { }
    ) {
        Row(modifier = Modifier.padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = status, color = if (status == "Checked In") Color.Green else Color.Red)
        }
    }
}

