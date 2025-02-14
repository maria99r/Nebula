package com.alanturing.nebula.viewModel.authentication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alanturing.nebula.model.activities.ActivityRepository
import com.alanturing.nebula.model.activities.ActivityResponse
import com.alanturing.nebula.model.activities.ParticipationResponse
import com.alanturing.nebula.model.authentication.AuthRepository
import com.alanturing.nebula.viewModel.authentication.ViewModelAuth.Companion.EMAIL
import com.alanturing.nebula.viewModel.authentication.ViewModelAuth.Companion.REFRESH_TOKEN
import com.alanturing.nebula.viewModel.authentication.ViewModelAuth.Companion.ROLE
import com.alanturing.nebula.viewModel.authentication.ViewModelAuth.Companion.TOKEN
import com.alanturing.nebula.viewModel.authentication.ViewModelAuth.Companion.USERID

import com.alanturing.nebula.viewModel.authentication.ViewModelAuth.Companion.dataStorageAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class ViewModelActivities(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    private val repository = ActivityRepository()

    private var _allActivities = MutableStateFlow<List<ActivityResponse>>(emptyList())
    var allActivities : StateFlow<List<ActivityResponse>>  = _allActivities

    private var _userActivities = MutableStateFlow<List<ParticipationResponse>>(emptyList())
    var userActivities : StateFlow<List<ParticipationResponse>>  = _userActivities

    private var _accessToken = MutableStateFlow("")
    var accessToken : StateFlow<String>  = _accessToken

    private var _userId = MutableStateFlow(0)
    var userId : StateFlow<Int> = _userId


    init {
        loadCredentials()
    }


    // esta carga credenciales del data storage
    fun loadCredentials(){
        viewModelScope.launch {
            context.dataStorageAuth.data.collect { preferences ->
                _accessToken.value = preferences[TOKEN] ?: ""
                _userId.value = preferences[USERID] ?: 0
            }
        }
    }


    fun getAllActivities (){
        viewModelScope.launch {
                _allActivities.value = repository.getAllActivities(accessToken.value)
            }
        }


    fun createParticipation (activityId : Int){
        viewModelScope.launch {
            if(userId.value == 0 || activityId == 0){
                Log.i ("crear participacion", "algun id es 0")
            }  else {

                val result = repository.addParticipation(userId.value, activityId, accessToken.value)

                if(result.id == 0 || result.userId == 0  || result.activityId == 0  ){
                    Log.i ("crear participacion", "el resultado no crea bien, algun id es 0")
                }
                else {
                    getUserActivities()
                    getAllActivities()
                    Log.i("se crea bien la participacion" , result.toString())
                }
            }
        }
    }

    fun deleteParticipation (id : Int){
        viewModelScope.launch {
            if(id == 0 ){
                Log.i ("borrar participacion", "id es 0")
            }  else {

                val result = repository.deleteParticipationById(accessToken.value, id)

                if(!result){
                    Log.i ("borrar participacion", "el resultado no borra bien")
                }
                else {
                    getUserActivities()
                    getAllActivities()
                    Log.i("borrar participacion", "se borra bien la participacion")
                }
            }
        }
    }

    fun getUserActivities() {
        viewModelScope.launch {
            viewModelScope.launch {
                _userActivities.value = repository.getActivitiesByUser(accessToken.value)
                Log.i("obtener actividad de usuario", _userActivities.value.toString())
            }
        }
    }
}


