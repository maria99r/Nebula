package com.alanturing.nebula.model.authentication

import com.alanturing.nebula.model.pokedex.DatosPokemon
import retrofit2.Response

class AuthRepository {
    private val authClient = AuthRetrofit.authclient

    suspend fun login( authRequest: AuthRequest ): Response<LoginResponse> {
        return authClient.login(authRequest)
    }

}