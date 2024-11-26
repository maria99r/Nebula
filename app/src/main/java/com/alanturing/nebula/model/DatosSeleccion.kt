package com.alanturing.nebula.model

public class DatosSeleccion (
    var checked: Boolean,
    var onCheckedChange: (Boolean) -> Unit = {},
    val label: String,
    var enabled: Boolean = true
)
