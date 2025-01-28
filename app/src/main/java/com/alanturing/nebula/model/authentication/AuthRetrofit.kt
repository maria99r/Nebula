package com.alanturing.nebula.model.authentication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRetrofit {

    private const val BASE_URL =  "http://10.0.2.2:8080"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authclient: AuthClient by lazy {
        retrofit.create(AuthClient::class.java)
    }

    // con datastore guardamos el token de acceso para q no tenga q iniciar sesion en cada pantalla.
}