package com.alanturing.nebula.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alanturing.nebula.model.misTareas.MiTarea
import com.alanturing.nebula.model.misTareas.RepositorioMisTareas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TareasViewModel(private val misTareasRepository: RepositorioMisTareas) : ViewModel() {
    fun getAll(): Flow<List<MiTarea>>
            = misTareasRepository.getAll()

    fun insertarTarea(tarea: String, b: Boolean) = viewModelScope.launch {
        misTareasRepository.insertarTarea(MiTarea(name = tarea, isChecked = b))
    }

    fun borrarTodasTareas(todasTareas: List<MiTarea>) = viewModelScope.launch {
        misTareasRepository.borrarTodasTareas(todasTareas)
    }

    fun updateChecked(tarea: MiTarea) = viewModelScope.launch {
        misTareasRepository.updateChecked(tarea)
    }

    fun borrarTarea(tarea: MiTarea) = viewModelScope.launch {
        misTareasRepository.borrarTarea(tarea)
    }

    fun getSelected(): Flow<List<MiTarea>>
            = misTareasRepository.getSelected()


    class Factory(private val repository: RepositorioMisTareas) : ViewModelProvider.Factory {
        override fun <T : ViewModel>
                create(modelClass: Class<T>):
                T {
            if (modelClass.isAssignableFrom(TareasViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TareasViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}