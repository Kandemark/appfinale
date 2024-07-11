package com.application.myapplication.ui.screens.payment

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.myapplication.network.SimulateRequest
import com.application.myapplication.viewmodel.PaymentViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.application.myapplication.ui.theme.Typography
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.android.gms.wallet.button.PayButton
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import kotlinx.coroutines.launch
import com.application.myapplication.common.CustomDefaultBtn
import com.application.myapplication.common.CustomTextField

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentScreen(navController: NavHostController, paymentViewModel: PaymentViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        paymentViewModel.getAccessToken("client_id", "client_secret")
    }

    var amount by remember { mutableStateOf("") }
    var recipient by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf<String?>(null) }
    var recipientError by remember { mutableStateOf<String?>(null) }

    val scaffoldState = rememberScaffoldState()
    val accessToken by paymentViewModel.accessToken.observeAsState()
    val paymentStatus by paymentViewModel.paymentStatus.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Payment Screen",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 32.dp)
            )
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    if (it.toIntOrNull() != null && it.toInt() > 0) {
                        amountError = null
                    }
                },
                label = { Text("Amount") },
                shape = RoundedCornerShape(1.dp),
                isError = amountError != null,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            if (amountError != null) {
                Text(
                    text = amountError!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = recipient,
                onValueChange = {
                    recipient = it
                    if (it.isNotBlank()) {
                        recipientError = null
                    }
                },
                label = { Text("Recipient") },
                shape = RoundedCornerShape(1.dp),
                isError = recipientError != null,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )
            if (recipientError != null) {
                Text(
                    text = recipientError!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            PayButton(onPayClick = {
                var newAmountError: String? = null
                var newRecipientError: String? = null

                val amountValue = amount.toIntOrNull()
                if (amountValue == null || amountValue <= 0) {
                    newAmountError = "Please enter a valid amount"
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Please enter a valid amount")
                    }
                } else {
                    newAmountError = null
                }

                if (recipient.isBlank()) {
                    newRecipientError = "Please enter a recipient"
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Please enter a recipient")
                    }
                } else {
                    newRecipientError = null
                }

                if (newAmountError == null && newRecipientError == null && !accessToken.isNullOrEmpty()) {
                    paymentViewModel.initiatePayment(amount, recipient)
                } else {
                    if (accessToken.isNullOrEmpty()) {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Access token is missing")
                        }
                    }
                }

                amountError = newAmountError
                recipientError = newRecipientError
            })

            paymentStatus?.let { status ->
                Text(
                    text = "Payment Status: $status",
                    style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary),
                    color = when (status) {
                        "Success" -> Color.Green
                        "Failure" -> Color.Red
                        else -> Color.Black
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PayButton(onPayClick: () -> Unit) {
//    Button(
//        onClick = onPayClick,
//        modifier = Modifier
//            .padding(horizontal = 16.dp)
//            .height(48.dp)
//    ) {
//        Text(text = "Request") // Replace with your button text
//    }

    CustomDefaultBtn(
        shapeSize = 50f,
        btnText = "Request",
        onClick = onPayClick,
    )
}












































//@Composable
//fun PaymentScreen(navController: NavHostController, paymentViewModel: PaymentViewModel = viewModel()) {
//
//    LaunchedEffect(Unit) {
//        paymentViewModel.getAccessToken("client_id", "client_secret")
//    }
//
//    var amount by remember { mutableStateOf(0) }
//    var recipient by remember { mutableStateOf("") }
//
//    val accessToken by paymentViewModel.accessToken.observeAsState()
//    val paymentStatus by paymentViewModel.paymentStatus.observeAsState()
//
//    val onPayClick: () -> Unit = {
//        if (!accessToken.isNullOrEmpty()) {
//            paymentViewModel.initiatePayment(amount.toString(), recipient)
//        } else {
//            // Handle missing access token
//        }
//    }
//
////    val onPayClick: () -> Unit = {
////        if (!accessToken.isNullOrEmpty()) {
////            val simulateRequest = SimulateRequest(amount.toString(), recipient)
////            paymentViewModel.simulatePayment(simulateRequest)
////        } else {
////            // Handle missing access token
////        }
////    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        TextField(
//            value = amount.toString(),
//            onValueChange = { amount = it.toIntOrNull() ?: 0 },
//            label = { Text("Amount") }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        TextField(
//            value = recipient,
//            onValueChange = { recipient = it },
//            label = { Text("Recipient") }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = onPayClick) {
//            Text("prompt")
//        }
//        paymentStatus?.let { status ->
//            Text(
//                text = "Payment Status: $status",
//                modifier = Modifier.padding(top = 16.dp)
//            )
//        }
//    }
//}

