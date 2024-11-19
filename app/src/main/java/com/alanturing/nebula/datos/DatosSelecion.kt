    package com.alanturing.nebula.datos;

    import kotlin.Unit;

    public class DatosSeleccion (
        var checked: Boolean,
        var onCheckedChange: (Boolean) -> Unit = {},
        val label: String,
        var enabled: Boolean = true
    )
