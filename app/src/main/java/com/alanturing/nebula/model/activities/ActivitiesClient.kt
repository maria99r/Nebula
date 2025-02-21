package com.alanturing.nebula.model.activities


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivitiesClient {

    // ACTIVIDADES ---------------------

    @GET("/api/activity")
    suspend fun getAllActivities(@Header("Authorization") bearerToken: String) : Response<List<ActivityResponse>>

    @GET("/api/participation/byUser/{userId}")
    suspend fun getActivityByUser(
        @Header("Authorization") bearerToken: String,
        @Path("userId") userId :Int)
    : Response<List<ActivityResponse>>

    // PARTICIPACIONES --------------------

    @POST("/api/participation")
    suspend fun addParticipation(
        @Header("Authorization") bearerToken: String,
        @Body participationRequest: ParticipationRequest
    ) : Response<ParticipationResponse>

    @DELETE("/api/participation/{activityId}/{userId}")
    suspend fun deleteParticipationById(
        @Header("Authorization") bearerToken: String,
        @Path("activityId") activityId: Int,
        @Path("userId") userId: Int
    ): Response<Boolean>
}