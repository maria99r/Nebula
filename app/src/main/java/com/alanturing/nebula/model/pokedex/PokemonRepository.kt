package com.alanturing.nebula.model.pokedex

class PokemonRepository {
    private val clientePokemon = RetrofitInstance.clientePokemon

    suspend fun getPokemons(): List<DatosPokemon> {
        return clientePokemon.getDatosPokemon()
    }
}