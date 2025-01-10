package com.alanturing.nebula.view

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alanturing.nebula.R
import com.alanturing.nebula.ui.theme.backgroundDark
import com.alanturing.nebula.viewModel.TareasViewModel


@Composable
fun Tareas(
    navController: NavHostController,
    viewModel: TareasViewModel
) {
    val listaMochila by viewModel.getAll().collectAsState(initial = emptyList())
    var tareaImput by remember { mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,

    ) {
        Text(
            text = stringResource(id = R.string.titulotareas),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista
        LazyColumn(
            modifier = Modifier.weight(.7F)
        ) {
            items(listaMochila){ tarea ->
                var isDone by rememberSaveable { mutableStateOf(tarea.isChecked) }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                )
                {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    )
                    { Checkbox(
                        checked = isDone,
                        onCheckedChange = {
                            isDone = it
                            tarea.isChecked = isDone
                            viewModel.updateChecked(tarea)
                        }
                    )
                        Text(
                            text = tarea.name,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textDecoration = if (isDone) TextDecoration.LineThrough else null,
                                color = if (isDone)
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                else MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error),
                            onClick = { viewModel.borrarTarea(tarea) })
                        {
                            Text(
                                text = Icons.Filled.Delete.toString())

                        }

                    }



                    /*Text(
                        text = tarea.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = if (isDone) TextDecoration.LineThrough else null,
                            color = if (isDone)
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            else MaterialTheme.colorScheme.onSurface ),
                    modifier = Modifier.padding(start = 8.dp) )*/
                }
            }

        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(.3F)
        ) {
            TextField(
                value = tareaImput,
                onValueChange = { tareaImput = it },
                label = { Text(text = stringResource(id = R.string.mochila)) },
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary),
                enabled = tareaImput.isNotBlank(),
                onClick = { viewModel.insertarTarea(tareaImput, false)
                    tareaImput = ""}
            ) {
                Text(text = stringResource(id = R.string.añadir))
            }
        }
        Row {

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error),
                enabled = listaMochila.isNotEmpty(),
                onClick = { viewModel.borrarTodasTareas(listaMochila) })
            {
                Text(
                    text = stringResource(id = R.string.borrartodo))
            }
        }
    }
}




