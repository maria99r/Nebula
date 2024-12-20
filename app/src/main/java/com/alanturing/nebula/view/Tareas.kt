package com.alanturing.nebula.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        // horizontalAlignment = Layout.Alignment.CenterHorizontally
    ) {
        Text(
            text = "Organiza qué vas a llevar a tu camping",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista tareas
        LazyColumn(
            modifier = Modifier.weight(.7F),
            verticalArrangement = Arrangement.Center
        ) {
            items(listaMochila){ tarea ->
                var isChecked by remember { mutableStateOf(false) }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                )
                { Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
                    Text(
                        text = tarea.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = if (isChecked) TextDecoration.LineThrough else null,
                            color = if (isChecked)
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            else MaterialTheme.colorScheme.onSurface ),
                    modifier = Modifier.padding(start = 8.dp) )
                }
            }

        }

        // Input field and buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(.3F)
        ) {
            OutlinedTextField(
                value = tareaImput,
                onValueChange = { tareaImput = it },
            )
            Button(
                onClick = { viewModel.insertarTarea(tareaImput) }
            ) {
                Text(text = "Añadir")
            }
            Button(
                onClick = { viewModel.borrarTodasTareas(listaMochila) })
            {
                Text(text = "Borrar todo")
            }
        }
    }
}




