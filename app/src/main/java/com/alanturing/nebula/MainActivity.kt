package com.alanturing.nebula

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.alanturing.nebula.model.misTareas.BaseDatosMisTareas
import com.alanturing.nebula.model.misTareas.RepositorioMisTareas
import com.alanturing.nebula.ui.theme.NebulaTheme
import com.alanturing.nebula.view.navigationdrawer.NavigationDrawer
import com.alanturing.nebula.viewModel.AuthViewModel
import com.alanturing.nebula.viewModel.PokemonViewModel
import com.alanturing.nebula.viewModel.TareasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NebulaTheme {
                Surface {
                    val navController = rememberNavController()
                    val authViewModel : AuthViewModel by viewModels()
                    val viewModel = PokemonViewModel()
                    val database = BaseDatosMisTareas.getMyDatabase(applicationContext)
                    val tareasRepository = RepositorioMisTareas(database.myDataDao())
                    val tareasViewModel: TareasViewModel = viewModel(factory = TareasViewModel.Factory(tareasRepository))
                    NavigationDrawer(navController, authViewModel, viewModel, tareasViewModel)
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
        // Principal(navController, authViewModel = AuthViewModel(applicationContext))
    }
}
