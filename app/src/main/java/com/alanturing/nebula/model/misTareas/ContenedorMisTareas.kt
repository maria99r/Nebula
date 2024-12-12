package com.alanturing.nebula.model.misTareas

import android.content.Context

class ContenedorMisTareas(private val context: Context) {
    val repositorioMisTareas: RepositorioMisTareas by lazy {
        RepositorioMisTareas(BaseDatosMisTareas.getMyDatabase(context).myDataDao())
    }
}