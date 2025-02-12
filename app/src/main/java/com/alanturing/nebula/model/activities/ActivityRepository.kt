package com.alanturing.nebula.model.activities

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class ActivityRepository {
/*
    private val activityClient = ActivitiesRetrofit

    suspend fun createActivity( email : String , password : String): ActivityResponse {
        return withContext(Dispatchers.IO) {
            val createData = activityClient.createActivity(AuthRequest(email , password))
            ActivityRequest(
                registerData.body()?.accessToken ?: "",
                registerData.body()?.refreshToken ?: "",
            )
        }
    }

    suspend fun refreshToken (tokenRequest: String) : TokenResponse {
        return withContext(Dispatchers.IO) {
            val refresh = authClient.refreshToken(TokenRequest(tokenRequest))
            TokenResponse(
                refresh.body()?.token ?: "",
            )
        }
    }

    suspend fun register( email : String , password : String ):SignUpResponse {
        return withContext(Dispatchers.IO) {
            val registerData = authClient.register(AuthRequest(email , password))
            SignUpResponse(
                registerData.body()?.id ?: 0,
                registerData.body()?.email ?: "",
                registerData.body()?.role ?: "",
            )
        }
    }

    suspend fun getByEmail(email : String, accestoken : String) :SignUpResponse {
        return withContext(Dispatchers.IO) {
            val data = authClient.getByEmail(email, "Bearer $accestoken")
            SignUpResponse(
                data.body()?.id ?: 0,
                data.body()?.email ?: "",
                data.body()?.role ?: "",
            )
        }
    }


    // ACTIVIDADES ---------------------

    @POST("/api/activity")
    suspend fun createActivity(
        // @Header("Authorization") bearerToken: String,
        @Body activityRequest: ActivityRequest
    ) : Response<ActivityResponse>

    @GET("/api/activity")
    suspend fun getAllActivities() : Response<List<ActivityResponse>>

    @GET("/api/activity/{activityId}")
    suspend fun getActivityById(
        @Path("iactivityId") id :Int
    ) : Response<ActivityResponse>

    @GET("/api/activity/{userId}")
    suspend fun getActivityByUserId( @Path("userId") userId :Int ) : Response<ActivityResponse>

    @DELETE("/api/activity/{id}")
    suspend fun deleteActivityById(@Path("id") id: Int): Response<Boolean>


    // PARTICIPACIONES --------------------

    @POST("/api/participation")
    suspend fun addParticipation( @Body participationRequest: ParticipationRequest
    ) : Response<ParticipationResponse>

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

    @DELETE("/api/participation/{id}")
    suspend fun deleteParticipationById(@Path("id") id: Int): Response<Boolean>
*/

}