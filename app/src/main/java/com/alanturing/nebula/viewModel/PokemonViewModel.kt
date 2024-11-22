package com.alanturing.nebula.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturing.nebula.model.pokedex.DatosPokemon
import com.alanturing.nebula.model.pokedex.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    private val repository = PokemonRepository()

    private val _pokemons = MutableLiveData<List<DatosPokemon>>()
    val pokemons: LiveData<List<DatosPokemon>> = _pokemons

    fun fetchPokemons() {
        viewModelScope.launch {
            try {
                val pokemons = repository.getPokemons()
                _pokemons.value = pokemons
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}