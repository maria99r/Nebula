package com.alanturing.nebula.model

sealed class Rutas(val ruta: String) {
    data object Principal : Rutas("principal")
    data object Configuracion : Rutas("configuracion")
    data object Ayuda : Rutas("ayuda")
    data object AcercaDe : Rutas("acercaDe")
    data object Pokedex : Rutas("pokedex")
}