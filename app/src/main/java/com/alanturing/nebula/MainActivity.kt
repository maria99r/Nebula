package com.alanturing.nebula

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alanturing.nebula.ui.theme.NebulaTheme
import com.alanturing.nebula.model.Rutas
import com.alanturing.nebula.view.AcercaDe
import com.alanturing.nebula.view.Ayuda
import com.alanturing.nebula.view.Configuracion
import com.alanturing.nebula.view.Pokemon
import com.alanturing.nebula.view.Principal
import com.alanturing.nebula.viewModel.PokemonViewModel

val Context.dataStore by preferencesDataStore("configuracion")


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NebulaTheme {
                Surface {
                     val navController = rememberNavController()
                    val viewModel = PokemonViewModel()
                    NavHost(navController = navController, startDestination = Rutas.Principal.ruta) {
                        composable(Rutas.Principal.ruta) { Principal(navController) }
                        composable(Rutas.Configuracion.ruta) { Configuracion(navController) }
                        composable(Rutas.Ayuda.ruta) { Ayuda(navController) }
                        composable(Rutas.AcercaDe.ruta) { AcercaDe(navController) }
                        composable(Rutas.Pokedex.ruta) { Pokemon(viewModel, navController) }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAcercade() {
    val navController = rememberNavController()
    NebulaTheme {
        Principal(navController)
    }
}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewAyuda() {
//    AppTheme {
//        Ayuda()
//    }
//}
