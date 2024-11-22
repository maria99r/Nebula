package com.alanturing.nebula.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alanturing.nebula.R
import com.alanturing.nebula.model.Ruta


// pagina principasl con botonces con las distintas paginas, con iconos y estilado
// + tarjeta pasra salir que sale un dialogo

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

@Composable
fun ImgConfiguracion() {
    val image = painterResource(R.drawable.configuracionicon)
    Image(
        modifier = Modifier.width(60.dp),
        painter = image,
        contentDescription = null
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Principal(navigationController: NavHostController) {

    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
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
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MenuCardButton(
                        icon = R.drawable.configuracionicon,
                        text = stringResource(id = R.string.configuracion),
                        onClick = { navigationController.navigate(Ruta.Configuracion.ruta) }
                    )
                    MenuCardButton(
                        icon = R.drawable.informacion,
                        text = stringResource(id = R.string.ayuda_titulo),
                        onClick = { navigationController.navigate(Ruta.Ayuda.ruta) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            // Segunda fila
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuCardButton(
                    icon = R.drawable.sobrenosotros,
                    text = stringResource(id = R.string.acerca_titulo),
                    onClick = { navigationController.navigate(Ruta.AcercaDe.ruta) }
                )
                MenuCardButton(
                    icon = R.drawable.salida,
                    text = stringResource(id = R.string.salir),
                    onClick = { showDialog = true }
                )
            }

        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.salir)) },
            text = { Text(text = stringResource(id = R.string.confirmar_salida)) },
            confirmButton = {
                Button(onClick = { System.exit(0) }) {
                    Text(text = stringResource(id = R.string.si))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = R.string.no))
                }
            }
        )
    }
}

//@Composable
//fun DialogoGenerico(
//    onDismissRequest: () -> Unit,
//    onConfirmation: () -> Unit,
//    dialogTitle: String,
//    dialogText: String,
//    icon: ImageVector
//){
//    AlertDialog(
//
//    )
//}


@Composable
fun MenuCardButton(
    icon: Int, // Resource ID for the icon
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .size(width = 170.dp, height = 170.dp)
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary
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
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun MenuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick,
        modifier = modifier.height(100.dp),
        shape = MaterialTheme.shapes.small,
        content = content
    )
}
