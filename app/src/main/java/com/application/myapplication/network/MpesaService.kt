package com.application.myapplication.network

import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Headers

interface MpesaService {
    @Headers("Authorization: Bearer <https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials>")
    @POST("mpesa/c2b/v1/simulate")
    suspend fun simulateTransaction(@Body request: SimulateRequest): SimulateResponse
}

data class SimulateRequest(
    val input: String, // Example property, replace with actual properties
    val recipient: String
)

data class SimulateResponse(
    val statusCode: Int,
    val message: String
)


