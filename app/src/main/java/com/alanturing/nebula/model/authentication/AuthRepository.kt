package com.alanturing.nebula.model.authentication

import retrofit2.Response

class AuthRepository {

    private val authClient = AuthRetrofit.authclient

    suspend fun login( authRequest: AuthRequest ): Response<LoginResponse> {
        return authClient.login(authRequest)
    }

    suspend fun refreshToken (tokenRequest: TokenRequest) : Response<TokenResponse> {
        return authClient.refreshToken(tokenRequest)
    }

    suspend fun register( authRequest: AuthRequest ): Response<SignUpResponse> {
        return authClient.register(authRequest)
    }

    suspend fun getByEmail (email : String, accestoken : String) : Response<SignUpResponse> {
        return authClient.getByEmail(email, "Bearer $accestoken")
    }

}