package com.alanturing.nebula.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alanturing.nebula.R
import com.alanturing.nebula.viewModel.TareasViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Tareas(
    navController: NavHostController,
    viewModel: TareasViewModel
) {
    val listaMochila by viewModel.getAll().collectAsState(initial = emptyList())
    var tareaImput by remember { mutableStateOf("") }

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
            items(
                listaMochila,
                key = { it.id } // id para cada tarea
            ) { tarea ->
                var isDone by rememberSaveable { mutableStateOf(tarea.isChecked) }
                var visible by remember { mutableStateOf(true) }
                val coroutineScope = rememberCoroutineScope()

                // Animacion
                AnimatedVisibility(
                    visible = visible,
                    exit = fadeOut(animationSpec =
                    tween(durationMillis = 1500)) + slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 1500)
                    ),
                    modifier = Modifier.animateContentSize()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isDone = !isDone
                                    tarea.isChecked = isDone
                                    viewModel.updateChecked(tarea)
                                }
                        ) {
                            Checkbox(
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
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(1f)
                            )
                            Button(
                                onClick = {
                                    visible = false
                                    coroutineScope.launch {
                                        delay(1500) // Espera a que termine la animación
                                        viewModel.borrarTarea(tarea)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = stringResource(id = R.string.borrar),
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }

        // Campo de texto y botón para agregar tareas
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
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                enabled = tareaImput.isNotBlank(),
                onClick = {
                    viewModel.insertarTarea(tareaImput, false)
                    tareaImput = ""
                }
            ) {
                Text(text = stringResource(id = R.string.añadir))
            }
        }

        // Botón para borrar todas las tareas
        Row {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                enabled = listaMochila.isNotEmpty(),
                onClick = { viewModel.borrarTodasTareas(listaMochila) }
            ) {
                Text(
                    text = stringResource(id = R.string.borrartodo)
                )
            }
        }
    }
}
