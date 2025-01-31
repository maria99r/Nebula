package com.alanturing.nebula.view.auth

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.alanturing.nebula.viewModel.AuthViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.alanturing.nebula.R
import com.alanturing.nebula.model.Rutas
import com.alanturing.nebula.viewModel.authentication.AuthState
import com.alanturing.nebula.viewModel.authentication.ViewModelAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController, viewModelAuth: ViewModelAuth) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val authState = viewModelAuth.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated ->
                navController.navigate(
                    Rutas.Principal.ruta
                )

            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text =  stringResource(id = R.string.signup),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            shape = MaterialTheme.shapes.large,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "")
            },
        )


        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            shape = MaterialTheme.shapes.large,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "")
            },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Lock else Icons.Filled.Face
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModelAuth.register(email, password)
            },
            enabled = authState.value != AuthState.Loading,
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary,
                disabledContentColor = MaterialTheme.colorScheme.error,
                disabledContainerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Text(text =  stringResource(id = R.string.signup),)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate(Rutas.InicioSesion.ruta)
        }) {
            Text(
                text =  stringResource(id = R.string.invita_inicia_sesion),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


