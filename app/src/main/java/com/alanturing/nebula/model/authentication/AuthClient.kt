package com.alanturing.nebula.model.authentication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthClient {

    @POST("/api/login")
    suspend fun login(@Body authRequest: AuthRequest) : Response<LoginResponse>

    @POST("/api/auth/refresh")
    suspend fun refreshToken(@Body token: TokenRequest) : LoginResponse

    @POST("/api/register")
    suspend fun register(@Body registerRequest: AuthRequest)

    /*la adquisición de los datos de un usuario específico (si el login solo devuelve tokens).*/
    @GET("/api/getUser/")
    suspend fun getByEmail(@Body token: TokenRequest) : SignUpResponse

}