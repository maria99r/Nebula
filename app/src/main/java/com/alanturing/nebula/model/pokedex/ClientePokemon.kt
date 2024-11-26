package com.alanturing.nebula.model.pokedex

import retrofit2.http.GET

interface ClientePokemon {
    @GET("/v3/50aca644-436c-4ef9-8887-b9aff7ef30a1")
    suspend fun getDatosPokemon(): List<DatosPokemon>
}
