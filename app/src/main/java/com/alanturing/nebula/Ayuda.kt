package com.alanturing.nebula

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


// IMAGEN LOGO
@Composable
fun ImgCamping() {
    val image = painterResource(R.drawable.campingnoche)
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = image,
        contentDescription = null
    )
}

// CONTENIDO PANTALLA ACERCA DE (logo, info autor y licencia)
@Composable
fun Ayuda(navController: NavController) {
    val context = LocalContext.current // para tener el contexto

    val titulo by remember {
        mutableStateOf(context.getString(R.string.ayuda_titulo))
    }
    val contenido by remember {
        mutableStateOf(context.getString(R.string.ayuda_contenido))
    }

    // scroll de pantalla
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState),
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

        Spacer(modifier = Modifier.height(16.dp))

        ImgCamping()

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido ayuda
        Text(
            text = contenido,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(20.dp)
                .padding(horizontal = 20.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}
