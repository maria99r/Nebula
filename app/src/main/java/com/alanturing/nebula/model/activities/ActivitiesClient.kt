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

    /*
    @GET("/api/activity/{activityId}")
    suspend fun getActivityById(
        @Path("iactivityId") id :Int
    ) : Response<ActivityResponse>*/


    @GET("/api/participation")
    suspend fun getActivityByUser(@Header("Authorization") bearerToken: String )
    : Response<List<ParticipationResponse>>

    /*
    @DELETE("/api/activity/{id}")
    suspend fun deleteActivityById(@Header("Authorization") bearerToken: String, @Path("id") id: Int): Response<Boolean>
*/

    // PARTICIPACIONES --------------------

    @POST("/api/participation")
    suspend fun addParticipation(@Header("Authorization") bearerToken: String,
                                 @Body participationRequest: ParticipationRequest
    ) : Response<ParticipationResponse>

    /*
    @GET("/api/participation/activity/{activityId}")
    suspend fun findParticipationsByActivityId(
        @Path("activityId") id :Int
    ) : Response<List<Participation>>


    @GET("/api/participation/user/{userId}")
    suspend fun findParticipationsByUserId(
        @Path("userId") id :Int
    ) : Response<List<ActivityResponse>>


    @GET("/api/participation")
    suspend fun findAllParticipations(): List<Participation>
    */

    @DELETE("/api/participation/{id}")
    suspend fun deleteParticipationById( @Header("Authorization") bearerToken: String,
        @Path("id") id: Int): Response<Boolean>


}