    package com.alanturing.nebula.model;

    import kotlin.Unit;

    public class DatosSeleccion (
        var checked: Boolean,
        var onCheckedChange: (Boolean) -> Unit = {},
        val label: String,
        var enabled: Boolean = true
    )
