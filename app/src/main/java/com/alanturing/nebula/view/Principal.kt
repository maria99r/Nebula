package com.alanturing.nebula.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
        modifier = Modifier.width(100.dp),
        painter = image,
        contentDescription = null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Principal(navigationController: NavHostController){

    var showDialog by remember { mutableStateOf(false) }

    Surface(modifier = Modifier
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
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { navigationController.navigate(Ruta.Configuracion.ruta) },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(id = R.string.configuracion))
            }
            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = { navigationController.navigate(Ruta.Ayuda.ruta) },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(id = R.string.ayuda))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navigationController.navigate(Ruta.AcercaDe.ruta) },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(id = R.string.acerca_de))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // salir
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp), shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(id = R.string.salir))
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
fun MenuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor =  MaterialTheme.colorScheme.primaryContainer,
            contentColor =  MaterialTheme.colorScheme.primary
        ),
        onClick = onClick,
        modifier = modifier.height(100.dp),
        shape = MaterialTheme.shapes.small,
        content = content
    )
}