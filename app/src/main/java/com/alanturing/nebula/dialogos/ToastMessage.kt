package com.alanturing.nebula.dialogos

import android.widget.Toast
import com.alanturing.nebula.R
import com.alanturing.nebula.model.Rutas

fun getStringResourceId(key : String) : Int {
    return when (key) {
        "formato_inadecuado" -> R.string.error_inicio_sesion
        "formato_vacio" -> R.string.error_vacio

        else -> {R.string.Error_Guardar} // aqui un error generico


    }
    Toast.makeText(
            context,
    context.getString(R.string.Error_Guardar),
    Toast.LENGTH_LONG
    ).show()
    navController.navigate(Rutas.Principal.ruta)
}