package com.alanturing.nebula.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.alanturing.nebula.viewModel.PokemonViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.alanturing.nebula.R
import com.alanturing.nebula.model.pokedex.DatosPokemon




@Composable
fun Pokemon(viewModel: PokemonViewModel, navigationController: NavHostController) {
    val pokemons by viewModel.pokemons.observeAsState(emptyList())

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchPokemons()
    }

    Column (
        modifier = Modifier.padding(top = 25.dp)
    ){
        Text(
            text = context.getString(R.string.pokedex),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp).padding(top = 20.dp)
        )
        if (pokemons.isEmpty()) {
            // Show loading indicator or placeholder
            Text(text = "Loading...")
        } else {
            // Display the list of pokemons
            PokemonList(pokemons)
            /*LazyColumn {
                items(pokemons) { pokemon ->
                    Text(text = pokemon.name)
                    Text(text = pokemon.num.toString())
                    Text(text = pokemon.type.toString())
                    HorizontalDivider() // Add a divider between items
                }
            }*/
        }
    }
}

@Composable
fun PokemonList(pokemonList: List<DatosPokemon>) {
    LazyColumn {
        itemsIndexed(items = pokemonList) { index, item ->
            PokemonItem(pokemon = item)
        }
    }
}


@Composable
fun PokemonItem(pokemon: DatosPokemon) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    ) {
        Row(
            Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemon.img.replace("http://", "https://"))
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder),
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .weight(0.3f),
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                        Text(
                            text = "#" + pokemon.num.toString() + "  " + pokemon.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = pokemon.type.toString(),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

            }
        }
    }



