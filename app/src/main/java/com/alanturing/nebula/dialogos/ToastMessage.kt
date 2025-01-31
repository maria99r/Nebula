package com.alanturing.nebula.dialogos

import com.alanturing.nebula.R

fun getStringResourceId(key : String) : Int {
    return when (key) {
        "formato_inadecuado" -> R.string.error_inicio_sesion

        else -> {R.string.Error_Guardar} // aqui un error generico
    }
}