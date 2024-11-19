package com.alanturing.nebula

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alanturing.nebula.model.Ruta


// pagina principasl con botonces con las distintas paginas, con iconos y estilado
// + tarjeta pasra salir que sale un dialogo

@Composable
fun Principal(navigationController: NavHostController){

    var showDialog by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = { navigationController.navigate(Ruta.Configuracion.ruta) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.configuracion))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navigationController.navigate(Ruta.Ayuda.ruta) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.ayuda))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navigationController.navigate(Ruta.AcercaDe.ruta) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.acerca_de))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // salir
            Button(
                onClick = { showDialog = true  },
                modifier = Modifier.fillMaxWidth()
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