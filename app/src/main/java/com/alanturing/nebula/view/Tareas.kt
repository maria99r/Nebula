package com.alanturing.nebula.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alanturing.nebula.viewModel.TareasViewModel

@Composable
fun Tareas(
    navController: NavHostController,
    viewModel: TareasViewModel = viewModel(factory = TareasViewModel.Factory) // ..1
) {
    val listaTareas by viewModel.getAll().collectAsState(initial = emptyList()) // ..2
    var tareaImput by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        // horizontalAlignment = Layout.Alignment.CenterHorizontally
    ) {
        // Lista tareas
        LazyColumn(
            modifier = Modifier.weight(.7F),
            verticalArrangement = Arrangement.Center
        ) {
            items(listaTareas){ tarea ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(80.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = tarea.name, style = MaterialTheme.typography.displaySmall)
                    }
                }
                Text(text = tarea.name, style = MaterialTheme.typography.displaySmall)
            }

        }

        // Input field and buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(.3F)
        ) {
            OutlinedTextField(value = tareaImput, onValueChange = { tareaImput = it })
            Button(onClick = { viewModel.insertarTarea(tareaImput) }) {
                Text(text = "SAVE")
            }
            Button(onClick = { viewModel.borrarTodasTareas(listaTareas) }) {
                Text(text = "ALL DELETE")
            }
        }
    }
}

