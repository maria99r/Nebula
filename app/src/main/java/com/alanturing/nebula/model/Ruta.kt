package com.alanturing.nebula.model

sealed class Ruta(val ruta: String) {
    data object Principal : Ruta("principal")
    data object Configuracion : Ruta("configuracion")
    data object Ayuda : Ruta("ayuda")
    data object AcercaDe : Ruta("acercaDe")
}