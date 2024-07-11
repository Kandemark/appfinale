package com.application.myapplication.viewmodel

import com.application.myapplication.model.MpesaPaymentRequest
import com.application.myapplication.model.MpesaPaymentResponse
import com.application.myapplication.network.RetrofitInstance

// src/main/java/com/example/myapplication/viewmodel/PaymentViewModel.kt

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.myapplication.network.AuthService
import com.application.myapplication.network.MpesaService
import com.application.myapplication.network.RetrofitClient
import com.application.myapplication.network.SimulateRequest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PaymentViewModel : ViewModel() {

    private val businessShortCode = "YOUR_BUSINESS_SHORTCODE"
    private val passkey = "YOUR_PASSKEY"
    private val callBackURL = "YOUR_CALLBACK_URL"
    private val transactionType = "CustomerPayBillOnline"
    private val accountReference = "YOUR_ACCOUNT_REFERENCE"
    private val transactionDesc = "Payment Description"

    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String> get() = _accessToken


    private val _paymentStatus = MutableLiveData<String>()
    val paymentStatus: LiveData<String> get() = _paymentStatus

    fun initiatePayment(amount: String, phoneNumber: String) {
        viewModelScope.launch {
            try {
                val timestamp = getCurrentTimestamp()
                val password = generatePassword(businessShortCode, passkey, timestamp)
                val request = MpesaPaymentRequest(
                    businessShortCode = businessShortCode,
                    password = password,
                    timestamp = timestamp,
                    transactionType = transactionType,
                    amount = amount,
                    partyA = phoneNumber,
                    partyB = businessShortCode,
                    phoneNumber = phoneNumber,
                    callBackURL = callBackURL,
                    accountReference = accountReference,
                    transactionDesc = transactionDesc
                )
                val response = RetrofitInstance.api.initiatePayment(request)
                handlePaymentResponse(response)
            } catch (e: Exception) {
                // Handle exceptions
                _paymentStatus.value = "Payment Failed: ${e.message}"
                e.printStackTrace()
            }
        }
    }

    private fun generatePassword(businessShortCode: String, passkey: String, timestamp: String): String {
        val data = "$businessShortCode$passkey$timestamp"
        return Base64.encodeToString(data.toByteArray(), Base64.NO_WRAP)
    }

    private fun getCurrentTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun handlePaymentResponse(response: MpesaPaymentResponse) {
        // Handle the response from M-Pesa
        if (response.resultCode == "0") {
            _paymentStatus.value = "Thank you for your payment!"
        } else {
            _paymentStatus.value = "Payment Failed: ${response.resultDesc}"
        }
    }

    private val authService = RetrofitClient.instance.create(AuthService::class.java)
    private val mpesaService = RetrofitClient.instance.create(MpesaService::class.java)

    fun getAccessToken(clientId: String, clientSecret: String) {
        viewModelScope.launch {
            try {
                val response = authService.getAccessToken(clientId, clientSecret)
                _accessToken.value = response.accessToken
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun simulatePayment(simulateRequest: SimulateRequest) {
        val token = accessToken.value


        if (token.isNullOrEmpty()) {
            // Handle missing access token
            return
        }

        viewModelScope.launch {
            try {
                // Set authorization header with access token
                _paymentStatus.value = "Payment Successful"
                val response = mpesaService.simulateTransaction(simulateRequest)
                // Process response and update UI
            } catch (e: Exception) {
                // Handle error
                _paymentStatus.value = "Payment Failed"
            }
        }
    }

}
