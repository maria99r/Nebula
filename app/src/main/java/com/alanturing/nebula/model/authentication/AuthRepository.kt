package com.alanturing.nebula.model.authentication

import com.alanturing.nebula.viewModel.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthRepository {

    private val authClient = AuthRetrofit.authclient

    suspend fun login( email : String , password : String): LoginResponse {
        return withContext(Dispatchers.IO) {
            val registerData = authClient.login(AuthRequest(email , password))
            LoginResponse(
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
                registerData.body()?.userId ?: 0,
                registerData.body()?.email ?: "",
                registerData.body()?.role ?: "",
            )
        }
    }

    suspend fun getByEmail(email : String, accestoken : String) :SignUpResponse {
        return withContext(Dispatchers.IO) {
            val data = authClient.getByEmail(email, "Bearer $accestoken")
            SignUpResponse(
                data.body()?.userId ?: 0,
                data.body()?.email ?: "",
                data.body()?.role ?: "",
            )
        }
    }

}