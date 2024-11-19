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
import com.alanturing.nebula.model.Ruta
import com.alanturing.nebula.view.AcercaDe
import com.alanturing.nebula.view.Ayuda
import com.alanturing.nebula.view.Configuracion
import com.alanturing.nebula.view.Principal

val Context.dataStore by preferencesDataStore("configuracion")


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NebulaTheme {

                Surface(
                    // modificadores, colores etc
                ) {
                     val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Ruta.Principal.ruta) {
                        composable(Ruta.Principal.ruta) { Principal(navController) }
                        composable(Ruta.Configuracion.ruta) { Configuracion(navController) }
                        composable(Ruta.Ayuda.ruta) { Ayuda(navController) }
                        composable(Ruta.AcercaDe.ruta) { AcercaDe(navController) }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAcercade() {
    val navController = rememberNavController()
    NebulaTheme {
        Configuracion(navController)
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
