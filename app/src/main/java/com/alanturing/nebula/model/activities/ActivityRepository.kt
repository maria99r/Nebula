package com.alanturing.nebula.model.activities

import com.alanturing.nebula.model.authentication.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Optional

class ActivityRepository {

    private val activityClient = ActivitiesRetrofit.activitiesClient

    /*
    suspend fun createActivity( name : String , description : String, place : String , token: String): ActivityResponse {
        return withContext(Dispatchers.IO) {
            val createData = activityClient.createActivity(
                "Bearer $token", ActivityRequest(name , description, place), )
            ActivityResponse(
                createData.body()?.id ?: 0,
                createData.body()?.name ?: "",
                createData.body()?.description ?: "",
                createData.body()?.place ?: ""
            )
        }
    }*/

    suspend fun getAllActivities(tokenRequest: String): List<ActivityResponse> {
        return withContext(Dispatchers.IO) {
            val response = activityClient.getAllActivities("Bearer $tokenRequest") // Si requiere token en header
            if (response.isSuccessful) {
                response.body()?.map { activity ->
                    ActivityResponse(
                        activity.id ?: 0,
                        activity.name ?: "",
                        activity.description ?: "",
                        activity.place ?: ""
                    )
                } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    suspend fun getActivitiesByUser(tokenRequest: String, userId: Int): List<ActivityResponse> {
        return withContext(Dispatchers.IO) {
            val response = activityClient.getActivityByUser("Bearer $tokenRequest", userId) // Si requiere token en header
            if (response.isSuccessful) {
                response.body()?.map { activity ->
                    ActivityResponse(
                        activity.id ?: 0,
                        activity.name ?: "",
                        activity.description ?: "",
                        activity.place ?: ""
                    )
                } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    // PARTICIPACIONES --------------------

    suspend fun addParticipation( userId: Int , activityId : Int, token : String): ParticipationResponse {
        return withContext(Dispatchers.IO) {
            val createData = activityClient.addParticipation(
                "Bearer $token",ParticipationRequest(userId , activityId))
            ParticipationResponse(
                createData.body()?.id ?: 0,
                createData.body()?.userId ?: 0,
                createData.body()?.activityId ?: 0,
                createData.body()?.activity ?: ActivityResponse(id = 0, name = "" , description = "" , place = "")
            )
        }
    }

    suspend fun deleteParticipationById(
        token: String, activityId : Int, userId: Int) :Boolean {
        return withContext(Dispatchers.IO) {
            val data = activityClient.deleteParticipationById("Bearer $token", activityId, userId)
            data.isSuccessful
        }
    }

}