package com.alanturing.nebula.viewModel.authentication

import android.annotation.SuppressLint
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
import com.alanturing.nebula.model.authentication.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class ViewModelAuth(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    private val repository = AuthRepository()

    companion object{
        val Context.dataStorageAuth : DataStore<Preferences> by preferencesDataStore(name = "dataStorageAuth")

        private val TOKEN = stringPreferencesKey("token")  // TOKEN DE ACCESO DEL USUARIO
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")  // TOKEN DE REFRESCO DEL USUARIO
        private val EMAIL = stringPreferencesKey("email")  // EMAIL DEL USUARIO
        private val USERID = intPreferencesKey("userId")  // ID DEL USUARIO
        private val ROLE = stringPreferencesKey("role")  // ROL DEL USUARIO
    }

    // esta instancia se guarda con singletone
    /*val dataStorageAuth = ConfiguracionDataStore(context)*/


    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    // variables de tipo mutableStateFlow y StateFlow en vez de tipo MutableLiveData y LiveData
    private var _accessToken = MutableLiveData<String>()
    var accessToken : LiveData<String> = _accessToken

    private var _refresToken = MutableLiveData<String>()
    var refreshToken : LiveData<String> = _refresToken

    private var _email = MutableLiveData<String>()
    var email : LiveData<String> = _email

    private var _userId = MutableLiveData<Int>()
    var userId : LiveData<Int> = _userId

    private var _role = MutableLiveData<String>()
    var role : LiveData<String> = _role


    // para get y set es igual q en el otro,
    // lo que pasa q no ha creado una funcion de get y set pero el codigo es ifual

    init {
        loadCredentials()
    }


    // esta carga credenciales del data storage
    fun loadCredentials(){
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            _accessToken.value = context.dataStorageAuth.data.map {
                    preferences ->  preferences[TOKEN] ?: ""
            }

            // cargo los 4 valores q guardo

        }
    }


    // cuando me autentico, solo me devuelve el token,
    // por eso guardo el email para q me diga luego en que actividades esta autenticado
    fun getUserDataAndSave(email : String){
        viewModelScope.launch{
            val result = repository.getByEmail(email, _accessToken.value.toString())

            if(result.userId == 0 || result.role .isEmpty() || result.email.isEmpty()){
                _authState.value = AuthState.Error("No existe usuario o no hay datos de este")
            } else {
                _userId.value = result.userId
                _email.value = result.email
                _role.value = result.role
                saveCredentials()
            }
        }
    }





    fun login(email : String, password : String){
        viewModelScope.launch {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                _authState.value = AuthState.Error("El email no es válido")

            if(email.isEmpty() || password.isEmpty()){
                _authState.value = AuthState.Error("Email or password can't be empty")
            }
            _authState.value = AuthState.Loading

            val response = repository.login(email, password)

            if (response.refreshToken.isEmpty() || response.accessToken.isEmpty() ){
                _authState.value = AuthState.Error( "Something went wrong")
                signOut()
            } else  {
                _authState.value = AuthState.Authenticated
                _refresToken.value = response.refreshToken
                _accessToken.value = response.accessToken
                getUserDataAndSave(email)
            }
        }
    }

    fun register(email : String, password : String){

        viewModelScope.launch {
            if(email.isEmpty() || password.isEmpty()){
                _authState.value = AuthState.Error("Email or password can't be empty")
            }

            _authState.value = AuthState.Loading

            val result = repository.register(email, password)

            if(result.email.isEmpty() || result.userId == 0  || result.role.isEmpty() ){
                _authState.value = AuthState.Error("Something went wrong")
                signOut()
            } else {
                _authState.value = AuthState.Authenticated
                login(email, password)
            }
        }

    }

    fun refreshAndSaveToken(){
        viewModelScope.launch {

            _authState.value = AuthState.Loading

            val response = repository.refreshToken(_refresToken.value.toString())

            if (response.token.isEmpty() ){
                _authState.value = AuthState.Error( "Something went wrong")
                signOut()
            } else  {
                _authState.value = AuthState.Authenticated
                _accessToken.value = response.token
            }

        }
    }

    suspend fun signOut(){
        _authState.value = AuthState.Unauthenticated
        context.dataStorageAuth.edit {
                preferences ->
            // borro los valores
            preferences[EMAIL] = ""
            preferences[TOKEN] = ""
            preferences[REFRESH_TOKEN] = ""
            preferences[USERID] = 0
        }
    }

    suspend fun saveCredentials(){
        context.dataStorageAuth.edit {
                preferences ->
            // guardo los valores
            preferences[EMAIL] = _email.value!!
            preferences[TOKEN] = _accessToken.value!!
            preferences[REFRESH_TOKEN] = _refresToken.value!!
            preferences[USERID] = _userId.value!!
            preferences[ROLE] = _role.value!!

            Log.d("DataStore", "Token recuperado: $preferences[TOKEN]")
            Log.d("DataStore", "Token refresco: $preferences[REFRESH_TOKEN]")
            Log.d("DataStore", "Email recuperado: $preferences[EMAIL]")
            Log.d("DataStore", "ID recuperado: $preferences[USERID]")
            Log.d("DataStore", "ROL recuperado: $preferences[ROLE]")
        }
    }
}


sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}