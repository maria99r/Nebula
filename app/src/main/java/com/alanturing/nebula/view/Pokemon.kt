package com.alanturing.nebula.view

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.alanturing.nebula.viewModel.PokemonViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.navigation.NavHostController


@Composable
fun Pokemon(viewModel: PokemonViewModel, navigationController: NavHostController) {
    val pokemons by viewModel.pokemons.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchPokemons()
    }

    Column {
        if (pokemons.isEmpty()) {
            // Show loading indicator or placeholder
            Text(text = "Loading...")
        } else {
            // Display the list of pokemons
            LazyColumn {
                items(pokemons) { pokemon ->
                    Text(text = pokemon.name)
                    Text(text = pokemon.num.toString())
                    Text(text = pokemon.type.toString())
                    HorizontalDivider() // Add a divider between items
                }
            }
        }
    }
}


