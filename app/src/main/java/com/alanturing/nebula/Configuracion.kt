package com.alanturing.nebula

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alanturing.nebula.datos.ConfiguracionDataStore
import com.alanturing.nebula.datos.DatosSeleccion
import kotlinx.coroutines.launch


@SuppressLint("MutableCollectionMutableState")
@Composable
fun Configuracion(navController: NavController) {
    val context = LocalContext.current
    val configuracion = ConfiguracionDataStore(context)
    val scope = rememberCoroutineScope()

    // configurables
    var tipoAlojamientoSeleccionado by rememberSaveable {  mutableIntStateOf(0) }
    var recibirNotificaciones by rememberSaveable { mutableStateOf(false) }
    var actividadSeleccionada by rememberSaveable { mutableStateOf("") }

    // lista de ciudades checkbox
    val ciudades = listOf(
        context.getString(R.string.ciudad1),
        context.getString(R.string.ciudad4),
        context.getString(R.string.ciudad5),
        context.getString(R.string.ciudad6)
    )

    // Crear lista mutable de opciones de ciudades
    val opcionesCiudades = ciudades.map {
        val checked = rememberSaveable { mutableStateOf(false) }
        DatosSeleccion(
            checked = checked.value,
            onCheckedChange = { checked.value = it },
            label = it,
        )
    }


    LaunchedEffect(Unit) {
        configuracion.getCiudadesSeleccionadas.collect { ciudadesGuardadas ->
            opcionesCiudades.forEach { opcion ->
                val ciudadGuardada = ciudadesGuardadas.find { it.label == opcion.label }
                if (ciudadGuardada != null) {
                    // Actualizar el estado de la opción
                    opcion.checked = ciudadGuardada.checked
                    opcion.onCheckedChange = { isChecked ->
                        opcion.checked = isChecked
                        scope.launch {
                            configuracion.saveCiudadesSeleccionadas(opcionesCiudades)
                        }
                    }
                }
            }
        }
    }


    // lista actividades - dropdown
    val opcionesActividades = listOf(
        context.getString(R.string.actividad1),
        context.getString(R.string.actividad2),
        context.getString(R.string.actividad3),
        context.getString(R.string.actividad4)
    )

    // cargo los datos del dataStore
    LaunchedEffect(Unit) {
        configuracion.getAlojamiento.collect { tipoAlojamientoSeleccionado = it ?: 0 }
        configuracion.getNotificaciones.collect { recibirNotificaciones = it }
        configuracion.getActividad.collect { actividadSeleccionada = it ?: "" }
    }

    // titulos de los apartados
    val titulo by remember { mutableStateOf(context.getString(R.string.configuracion_titulo)) }
    val tituloTipoTienda by remember { mutableStateOf(context.getString(R.string.select_tipo_tienda)) }
    val tituloSeleccionaCiudad by remember { mutableStateOf(context.getString(R.string.select_ciudad)) }
    val tituloActividad by remember { mutableStateOf(context.getString(R.string.alquilar_actividades)) }
    val tituloNotificaciones by remember { mutableStateOf(context.getString(R.string.recibir_notificaciones)) }
    val tituloGuardarConfi by remember { mutableStateOf(context.getString(R.string.guardar_configuracion)) }

    // scroll de pantalla
    val scrollState = rememberScrollState()

    // IMAGEN LOGO
    @Composable
    fun ImgCamping() {
        val image = painterResource(R.drawable.img_camping_bajo)
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = image,
            contentDescription = null
        )
    }


    // ELEMENTOS PANTALLA
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.height(50.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {

            // TITULO PAGINA
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        DespliegaRadioButton (
            texto = tituloTipoTienda,
            opciones = listOf("Bungalow", "Tienda de campaña"),
            radioButtonSeleccionado = tipoAlojamientoSeleccionado,
            alSeleccionar = { tipoAlojamientoSeleccionado = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CIUDADES CHECHBOX
        CheckboxList(options = opcionesCiudades, listTitle = tituloSeleccionaCiudad)

        Spacer(modifier = Modifier.height(16.dp))

        // Alquilar actividades - Dropdown
        DespliegaDropdown(
            texto = tituloActividad,
            opciones = opcionesActividades,
            opcionSeleccionada = actividadSeleccionada,
            alSeleccionar = {  actividad -> actividadSeleccionada = actividad  }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Preferencia notificaciones - Switch
        DespliegaSwitch(
            texto = tituloNotificaciones,
            switchSeleccionado = recibirNotificaciones,
            alSeleccionar = { recibirNotificaciones = it }
        )

        Spacer(modifier = Modifier.height(8.dp))


        // BOTON para guardar configuraciones
        Button(onClick = {
            scope.launch {
                configuracion.saveAlojamiento(tipoAlojamientoSeleccionado)
                configuracion.saveActividad(actividadSeleccionada)
                configuracion.saveNotificaciones(recibirNotificaciones)
                configuracion.saveCiudadesSeleccionadas(opcionesCiudades)
                // Comprobación: Mostrar Toast de acuerdo a la condición
                if (tipoAlojamientoSeleccionado == 0 && actividadSeleccionada.isNotEmpty()) {
                    Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Faltan datos por completar", Toast.LENGTH_SHORT).show()
                }
                navController.navigate("principal")
            }
        }) {
            Text(
                text = tituloGuardarConfi
            )
        }

    }
}


@Composable
fun CheckboxList(options: List<DatosSeleccion>, listTitle: String) {
    Column {
        Text(
            listTitle,
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.size(16.dp))
        options.forEach { option ->
            LabelledCheckbox(
                checked = option.checked,
                onCheckedChange = option.onCheckedChange,
                label = option.label,
                enabled = option.enabled
            )
        }
    }
}
@Composable
fun LabelledCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    Row(
        modifier = modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = colors
        )
        Spacer(Modifier.width(32.dp))
        Text(label)
    }
}

// Crea un checkbox
@Composable
fun DespliegaCheckBox(
    texto : String, // nombre,
    checkboxSeleccionado : Boolean,  // si está marcado o no
    alSeleccionar : (Boolean) -> Unit  // funcion que modifica la variable de estado
){
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkboxSeleccionado,
                onCheckedChange = { isChecked
                    -> alSeleccionar(isChecked) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.secondary
                )
            )
            // Texto del checkbox
            Text(
                text = texto,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// crea un switch
@Composable
fun DespliegaSwitch(
    texto: String, // Nombre o etiqueta del switch
    switchSeleccionado: Boolean, // Estado actual del switch
    alSeleccionar: (Boolean) -> Unit // Callback para actualizar el estado
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(10.dp))
        Switch(
            checked = switchSeleccionado,
            onCheckedChange = { alSeleccionar(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary
            )
        )

    }
}


// crea lista desplegable
@Composable
fun DespliegaDropdown(
    texto: String, // Nombre o etiqueta del Dropdown
    opciones: List<String>, // Lista de opciones para seleccionar
    opcionSeleccionada: String, // Opción actualmente seleccionada
    alSeleccionar: (String) -> Unit // Callback para actualizar la opción seleccionada
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Box {
            // Botón para desplegar el menú
            TextButton(onClick = { expanded = true }) {
                Text(text = opcionSeleccionada.ifEmpty { "Selecciona una opción" })
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            alSeleccionar(opcion)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun DespliegaRadioButton(
    texto : String, // nombre,
    opciones: List<String>, // Lista de opciones para RadioButton
    radioButtonSeleccionado : Int, // opcion seleccionada
    alSeleccionar : (Int) -> Unit  // actualiza la seleccion
){
    Column {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        opciones.forEachIndexed() { index, opcion ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = index == radioButtonSeleccionado,
                    onClick = { alSeleccionar(index) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = opcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}