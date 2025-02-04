package com.alanturing.nebula.dialogos

import com.alanturing.nebula.R

fun getStringResourceId(key : String) : Int {
    return when (key) {
        "formato_inadecuado" -> R.string.error_inicio_sesion
        "formato_vacio" -> R.string.error_vacio
        "contraseña_corta" -> R.string.error_contra
        "error_crear_usuario" -> R.string.error_crear_usuario
        "usuario_no_existe" -> R.string.error_usuario_no_existe
        else -> {R.string.error_generico}
    }
}


