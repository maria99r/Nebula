package com.alanturing.nebula.model.pokedex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = " https://dog.ceo/dog-api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val clientePokemon: ClientePokemon by lazy {
        retrofit.create(ClientePokemon::class.java)
    }
}