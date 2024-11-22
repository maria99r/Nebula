package com.alanturing.nebula.model.pokedex

import retrofit2.http.GET

interface ClientePokemon {
    @GET("pokemos")
    suspend fun getDatosPokemon(): List<DatosPokemon>
}
