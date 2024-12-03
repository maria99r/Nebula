package com.alanturing.nebula.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alanturing.nebula.R
import com.alanturing.nebula.dialogos.DialogAlertGeneric
import com.alanturing.nebula.model.Rutas
import com.alanturing.nebula.viewModel.AuthState
import com.alanturing.nebula.viewModel.AuthViewModel
import kotlin.system.exitProcess

// IMAGEN LOGO
@Composable
fun Logo() {
    val image = painterResource(R.drawable.logo)
    Image(
        modifier = Modifier.width(150.dp),
        painter = image,
        contentDescription = null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Principal(navigationController: NavHostController, authViewModel: AuthViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Unauthenticated) {
             navigationController.navigate(Rutas.InicioSesion.ruta)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            // verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Logo()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Primera fila
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    MenuCardButton(
                        icon = R.drawable.pokedex,
                        text = stringResource(id = R.string.pokedex),
                        onClick = { navigationController.navigate(Rutas.Pokedex.ruta) }
                    )
                    MenuCardButton(
                        icon = R.drawable.informacion,
                        text = stringResource(id = R.string.ayuda_titulo),
                        onClick = { navigationController.navigate(Rutas.Ayuda.ruta) }
                    )
                }

                // Segunda fila
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MenuCardButton(
                        icon = R.drawable.sobrenosotros,
                        text = stringResource(id = R.string.acerca_de),
                        onClick = { navigationController.navigate(Rutas.AcercaDe.ruta) }
                    )
                    MenuCardButton(
                        icon = R.drawable.configuracionicon,
                        text = stringResource(id = R.string.configuracion),
                        onClick = { navigationController.navigate(Rutas.Configuracion.ruta) }
                    )
                }
                // Tercera fila
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (authState.value !is AuthState.Authenticated) {
                        MenuCardButton(
                            icon = R.drawable.login,
                            text = stringResource(id = R.string.login),
                            onClick = { navigationController.navigate(Rutas.InicioSesion.ruta) }
                        )
                    }

                    MenuCardButton(
                        icon = R.drawable.salida,
                        text = stringResource(id = R.string.salir),
                        onClick = { showDialog = true }
                    )
                }
            }
            // boton cerrar sesion
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (authState.value is AuthState.Authenticated) {
                    TextButton(
                        onClick = { authViewModel.signout() }
                    ) {
                        Text(text = stringResource(id = R.string.cerrarSesion))
                    }
                }
            }
        }
    }

    if (showDialog) {
        DialogAlertGeneric(
            onDismiss = { showDialog = false },
            title = stringResource(id = R.string.salir),
            text = stringResource(id = R.string.confirmar_salida),
            confirmText = stringResource(id = R.string.si),
            onConfirm = { exitProcess(0) },
            dismissText = stringResource(id = R.string.no)
        )
    }
}


@Composable
fun MenuCardButton(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // degradado colores
    val colorFondo = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .size(width = 170.dp, height = 170.dp)
            .padding(8.dp)
            // fondo degradado
            .background(
                Brush.linearGradient(
                    colors = colorFondo
                )
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(2.dp, Color.White),

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(colors = colorFondo)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


/*
@Composable
fun DespliegaCars(
    textos: List<String>,
    iconos: List<Int>,

)
*/
