package com.application.myapplication.network


import com.application.myapplication.model.MpesaPaymentRequest
import com.application.myapplication.model.MpesaPaymentResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("mpesa/stkpush/v1/processrequest")
    suspend fun initiatePayment(@Body request: MpesaPaymentRequest): MpesaPaymentResponse
}


object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://sandbox.safaricom.co.ke/") // Sandbox URL for testing
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
