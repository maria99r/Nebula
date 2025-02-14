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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class ViewModelAuth(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    private val repository = AuthRepository()

    companion object{
        val Context.dataStorageAuth : DataStore<Preferences> by preferencesDataStore(name = "dataStorageAuth")

        val TOKEN = stringPreferencesKey("token")  // TOKEN DE ACCESO DEL USUARIO
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")  // TOKEN DE REFRESCO DEL USUARIO
        val EMAIL = stringPreferencesKey("email")  // EMAIL DEL USUARIO
        val USERID = intPreferencesKey("userId")  // ID DEL USUARIO
        val ROLE = stringPreferencesKey("role")  // ROL DEL USUARIO
    }

    // esta instancia se guarda con singletone
    /*val dataStorageAuth = ConfiguracionDataStore(context)*/

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    // variables de tipo MutableStateFlow y StateFlow en vez de tipo MutableLiveData y LiveData
    private var _accessToken = MutableStateFlow("")
    var accessToken : StateFlow<String>  = _accessToken

    private var _refresToken = MutableStateFlow("")
    var refreshToken : StateFlow<String> = _refresToken

    private var _email = MutableStateFlow("")
    var email : StateFlow<String> = _email

    private var _userId = MutableStateFlow(0)
    var userId : StateFlow<Int> = _userId

    private var _role = MutableStateFlow("")
    var role : StateFlow<String> = _role


    // para get y set es igual q en el otro,
    // lo que pasa q no ha creado una funcion de get y set pero el codigo es ifual

    init {
        loadCredentials()
    }


    // esta carga credenciales del data storage
    fun loadCredentials(){
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            _accessToken.value = context.dataStorageAuth.data.map { preferences ->
                preferences[TOKEN] ?: "" }.first()

            _refresToken.value = context.dataStorageAuth.data.map { preferences ->
                preferences[REFRESH_TOKEN] ?: "" }.first()

            _email.value = context.dataStorageAuth.data.map { preferences ->
                preferences[EMAIL] ?: "" }.first()

            _userId.value = context.dataStorageAuth.data.map { preferences ->
                preferences[USERID] ?: 0 }.first()

            _role.value = context.dataStorageAuth.data.map { preferences ->
                preferences[ROLE] ?: "" }.first()
        }
        if (_email.value.isEmpty()){
            Log.i("carga credenciales" , "email esta vacio")
            _authState.value = AuthState.Unauthenticated
        } else _authState.value = AuthState.Authenticated


    }

    // cuando me autentico, solo me devuelve el token,
    // por eso guardo el email para q me diga luego en que actividades esta autenticado
    fun getUserDataAndSave(email : String){
        viewModelScope.launch{
            val result = repository.getByEmail(email, _accessToken.value)

            Log.i("getUser" , result.toString())

            if(result.id == 0  || result.email.isEmpty()){
                _authState.value = AuthState.Error("No existe usuario o no hay datos de este")
            } else {
                _userId.value = result.id
                _email.value = result.email
                _role.value = result.role
                saveCredentials()
            }
        }
    }


    fun login(email : String, password : String){
        viewModelScope.launch {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _authState.value = AuthState.Error("formato_inadecuado")
            }
            else if(email.isEmpty() || password.isEmpty()){
                _authState.value = AuthState.Error("formato_vacio")
            }
            else if( password.length < 6){
                _authState.value = AuthState.Error("contraseña_corta")
            }else {

                _authState.value = AuthState.Loading

                val response = repository.login(email, password)

                Log.i("TOKENES" ,response.toString())

                if (response.refreshToken.isEmpty() || response.accessToken.isEmpty() ){
                    _authState.value = AuthState.Error("usuario_no_existe")

                } else  {
                    _authState.value = AuthState.Authenticated
                    _refresToken.value = response.refreshToken
                    _accessToken.value = response.accessToken
                    getUserDataAndSave(email)
                }
            }
        }
    }

    fun register(email : String, password : String){

        Log.i("registro" , "ha entrado")
        viewModelScope.launch {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _authState.value = AuthState.Error("formato_inadecuado")

            } else if(email.isEmpty() || password.isEmpty()){
                _authState.value = AuthState.Error("formato_vacio")
            } else if(password.length < 6){
                _authState.value = AuthState.Error("contraseña_corta")
            } else {
                _authState.value = AuthState.Loading

                val result = repository.register(email, password)

                Log.i("usuario creado" , result.toString())

                if(result.email.isEmpty() || result.id == 0  || result.role.isEmpty() ){
                    _authState.value = AuthState.Error("error_generico")
                }
                else {
                    Log.i("se guarda" , result.toString())
                    login(email, password)
                }
            }
        }
    }

    fun refreshAndSaveToken(){
        viewModelScope.launch {

            _authState.value = AuthState.Loading

            val response = repository.refreshToken(_refresToken.value.toString())

            if (response.token.isEmpty() ){
                _authState.value = AuthState.Error( "formato_vacio")
            } else  {
                _authState.value = AuthState.Authenticated
                _accessToken.value = response.token
            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
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
    }

    suspend fun saveCredentials(){
        context.dataStorageAuth.edit {
                preferences ->
            // guardo los valores
            preferences[EMAIL] = _email.value
            preferences[TOKEN] = _accessToken.value
            preferences[REFRESH_TOKEN] = _refresToken.value
            preferences[USERID] = _userId.value
            preferences[ROLE] = _role.value

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