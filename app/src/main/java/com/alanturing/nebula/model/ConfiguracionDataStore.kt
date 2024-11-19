package com.alanturing.nebula.model;

import android.content.Context;
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alanturing.nebula.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConfiguracionDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "configuracion")

        private val TIPO_TIENDA = intPreferencesKey("tipo_tienda") // radio button
        private val ACTIVIDAD = stringPreferencesKey("alquilar_actividades")  // desplegable
        private val RECIBIR_NOTIFICACIONES = booleanPreferencesKey("recibir_notificaciones")  // switch

        private val CIUDAD1 = booleanPreferencesKey("ciudad1")
        private val CIUDAD4 = booleanPreferencesKey("ciudad4")
        private val CIUDAD5 = booleanPreferencesKey("ciudad5")
        private val CIUDAD6 = booleanPreferencesKey("ciudad6")
    }


    // checkbox ciudad1
    val getCiudad1: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[CIUDAD1]  ?: false
        }

    suspend fun saveCiudad1(seleccionado: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[CIUDAD1] = seleccionado
        }
    }

    // checkbox ciudad4
    val getCiudad4: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[CIUDAD4]  ?: false
        }

    suspend fun saveCiudad4(seleccionado: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[CIUDAD4] = seleccionado
        }
    }

    // checkbox ciudad5
    val getCiudad5: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[CIUDAD5]  ?: false
        }

    suspend fun saveCiudad5(seleccionado: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[CIUDAD5] = seleccionado
        }
    }

    // checkbox ciudad6
    val getCiudad6: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[CIUDAD6]  ?: false
        }

    suspend fun saveCiudad6(seleccionado: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[CIUDAD6] = seleccionado
        }
    }


    // obtener ciudades seleccionadas
    val getCiudadesSeleccionadas: Flow<List<DatosSeleccion>> = context.dataStore.data
        .map { preferences ->
            val ciudades = listOf(
                context.getString(R.string.ciudad1),
                context.getString(R.string.ciudad4),
                context.getString(R.string.ciudad5),
                context.getString(R.string.ciudad6)
            )
            ciudades.map { ciudad ->
                val checked = preferences[booleanPreferencesKey(ciudad)] ?: false
                DatosSeleccion(
                    label = ciudad,
                    checked = checked,
                )
            }
        }

    // guarda ciudades seleccionadas
    suspend fun saveCiudadesSeleccionadas(ciudades: List<DatosSeleccion>) {
        context.dataStore.edit { preferences ->
            ciudades.forEach { ciudad ->
                val key = booleanPreferencesKey(ciudad.label)
                preferences[key] = ciudad.checked
            }
        }
    }


    // obtener alojamiento guardado
    val getAlojamiento: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[TIPO_TIENDA] ?: 0
        }

    // guarda alojamiento
    suspend fun saveAlojamiento(seleccionado: Int) {
        context.dataStore.edit { preferences ->
            preferences[TIPO_TIENDA] = seleccionado
        }
    }

    // obtener actividad
    val getActividad: Flow<String?> = context.dataStore.data
        .map { preferences ->
            val actividad = preferences[ACTIVIDAD]
            Log.d("DataStore", "Actividad recuperada: $actividad")  // Para depurar
            actividad
        }

    // guarda actividad
    suspend fun saveActividad(actividad: String) {
        context.dataStore.edit { preferences ->
            preferences[ACTIVIDAD] = actividad
        }
    }

    // obtener notificacion
    val getNotificaciones: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[RECIBIR_NOTIFICACIONES] ?: false
        }

    // guarda notificacion
    suspend fun saveNotificaciones(notificar: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[RECIBIR_NOTIFICACIONES] = notificar
        }
    }


}



