package com.alanturing.nebula

import android.app.Application
import com.alanturing.nebula.model.misTareas.ContenedorMisTareas

class AplicacionTareas : Application() {
    lateinit var container: ContenedorMisTareas

    override fun onCreate() {
        super.onCreate()
        container = ContenedorMisTareas(this)
    }
}
