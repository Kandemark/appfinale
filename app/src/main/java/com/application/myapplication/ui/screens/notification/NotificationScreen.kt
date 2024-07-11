package com.application.myapplication.ui.screens.notification

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.application.myapplication.viewmodel.NotificationViewModel










@Composable
fun NotificationScreen(onBadgeCountChange: (Int) -> Unit) {
    val notificationViewModel: NotificationViewModel = hiltViewModel()
    val badgeCount by notificationViewModel.badgeCount.collectAsState()

    ShowScreen(badgeCount = badgeCount, onBadgeCountChange = onBadgeCountChange)
}

@Composable
fun ShowScreen(badgeCount: Int, onBadgeCountChange: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Badge Count: $badgeCount", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onBadgeCountChange(badgeCount + 1) }) {
            Text(text = "Increase Badge Count")
        }
    }
}

@Composable
fun MainScreen() {
    val notificationViewModel: NotificationViewModel = hiltViewModel()

    NotificationScreen(onBadgeCountChange = { newBadgeCount ->
        notificationViewModel.updateBadgeCount(newBadgeCount)
    })
}












//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun NotificationScreen(navController: NavHostController) {
//    val viewModel: NotificationViewModel = viewModel()
//    val notifications by viewModel.notifications.collectAsState()
//
//    Scaffold(
////        topBar = {
////            TopAppBar(
////                title = { Text("Notifications") },
////                modifier = Modifier.fillMaxWidth(),
////                navigationIcon = {
////                    IconButton(onClick = { navController.navigateUp() }) {
////                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
////                    }
////                }
////            )
////        }
//    ) {
//        if (notifications.isEmpty()) {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        } else {
//            LazyColumn(
//                contentPadding = PaddingValues(6.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(notifications) { notification ->
//                    NotificationItem(notification)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun NotificationItem(notification: com.application.myapplication.viewmodel.Notification) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//        .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier
//            .padding(16.dp)) {
//            Text(text = notification.title, style = MaterialTheme.typography.headlineSmall)
//            Spacer(modifier = Modifier.height(3.dp))
//            Text(text = notification.message, style = MaterialTheme.typography.bodyMedium)
//        }
//    }
//}
//

