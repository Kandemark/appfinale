package com.application.myapplication.network

import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field

interface AuthService {
    @FormUrlEncoded
    @POST("oauth/v1/generate?grant_type=client_credentials")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AccessTokenResponse
}

data class AccessTokenResponse(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int
)



