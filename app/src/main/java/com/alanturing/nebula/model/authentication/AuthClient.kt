package com.alanturing.nebula.model.authentication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthClient {

    @POST("/api/auth")
    suspend fun login(@Body authRequest: AuthRequest) : Response<LoginResponse>

    @POST("/api/auth/refresh")
    suspend fun refreshToken(@Body tokenRequest: TokenRequest) : Response<TokenResponse>

    @POST("/api/user")
    suspend fun register(@Body authRequest: AuthRequest) : Response<SignUpResponse>

    @GET("/api/user/email/{email}")
    suspend fun getByEmail(
        @Path("email") email :String,
        @Header("Authorization") bearerToken: String
    ) : Response<SignUpResponse>

}