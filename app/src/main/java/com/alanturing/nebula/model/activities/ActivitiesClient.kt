package com.alanturing.nebula.model.activities

import com.alanturing.nebula.model.authentication.AuthRequest
import com.alanturing.nebula.model.authentication.LoginResponse
import com.alanturing.nebula.model.authentication.SignUpResponse
import com.alanturing.nebula.model.authentication.TokenRequest
import com.alanturing.nebula.model.authentication.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivitiesClient {

    // ACTIVIDADES ---------------------

    @POST("/api/activity")
    suspend fun createActivity(
        @Header("Authorization") bearerToken: String,
        @Body activityRequest: ActivityRequest
    ) : Response<ActivityResponse>

    @GET("/api/activity")
    suspend fun getAllActivities(@Header("Authorization") bearerToken: String) : Response<List<ActivityResponse>>

    @GET("/api/participation/byUser/{id}")
    suspend fun getActivityByUser(
        @Header("Authorization") bearerToken: String,
        @Path("userId") id :Int)
    : Response<List<ActivityResponse>>

    // PARTICIPACIONES --------------------

    @POST("/api/participation")
    suspend fun addParticipation(@Header("Authorization") bearerToken: String,
                                 @Body participationRequest: ParticipationRequest
    ) : Response<ParticipationResponse>

    @DELETE("/api/participation/{id}")
    suspend fun deleteParticipationById( @Header("Authorization") bearerToken: String,
        @Path("id") id: Int): Response<Boolean>
}