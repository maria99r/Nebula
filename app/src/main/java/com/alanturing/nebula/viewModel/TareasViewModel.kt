package com.alanturing.nebula.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alanturing.nebula.AplicacionTareas
import com.alanturing.nebula.model.misTareas.MiTarea
import com.alanturing.nebula.model.misTareas.RepositorioMisTareas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TareasViewModel(private val misTareasRepository: RepositorioMisTareas) : ViewModel() {
    fun getAll(): Flow<List<MiTarea>>
            = misTareasRepository.getAll()

    fun insertarTarea(tarea: String) = viewModelScope.launch {
        misTareasRepository.insertarTarea(MiTarea(name = tarea))
    }

    fun borrarTodasTareas(todasTareas: List<MiTarea>) = viewModelScope.launch {
        misTareasRepository.borrarTodasTareas(todasTareas)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AplicacionTareas)
                TareasViewModel(application.container.repositorioMisTareas)
            }
        }
    }
}