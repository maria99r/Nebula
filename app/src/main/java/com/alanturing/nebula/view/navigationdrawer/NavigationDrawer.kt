package com.alanturing.nebula.view.navigationdrawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import com.alanturing.nebula.model.Rutas
import com.alanturing.nebula.view.auth.InicioSesion
import com.alanturing.nebula.view.auth.Registro
import com.alanturing.nebula.viewModel.AuthViewModel
import com.alanturing.nebula.viewModel.PokemonViewModel
import com.alanturing.nebula.viewModel.TareasViewModel

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.alanturing.nebula.R
import com.alanturing.nebula.view.AcercaDe
import com.alanturing.nebula.view.Ayuda
import com.alanturing.nebula.view.Configuracion
import com.alanturing.nebula.view.Pokemon
import com.alanturing.nebula.view.Principal
import com.alanturing.nebula.view.Tareas
import com.alanturing.nebula.viewModel.authentication.ViewModelAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    navController: NavHostController,
    viewModelAuth: ViewModelAuth,
    viewModel: PokemonViewModel,
    tareasViewModel: TareasViewModel
) {
    val tareasNoSeleccionadas = tareasViewModel.getNoSelected().collectAsState(initial = emptyList()).value

    val items = listOf(
        NavigationItems(
            title = stringResource(id = R.string.Inicio),
            route = Rutas.Principal.ruta,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItems(
            title =  stringResource(id = R.string.acerca_titulo),
            route = Rutas.AcercaDe.ruta,
            selectedIcon = Icons.Filled.AccountBox,
            unselectedIcon = Icons.Outlined.AccountBox
        ),
        NavigationItems(
            title =  stringResource(id = R.string.tareas),
            route = Rutas.Tareas.ruta,
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
            badgeCount = if (tareasNoSeleccionadas.isNotEmpty()) tareasNoSeleccionadas.size else null
        )
        ,
        NavigationItems(
            title = stringResource(id = R.string.configuracion),
            route = Rutas.Configuracion.ruta,
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
        NavigationItems(
            title =  stringResource(id = R.string.ayuda_titulo),
            route = Rutas.Ayuda.ruta,
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        ),
        NavigationItems(
            title =  stringResource(id = R.string.pokedex),
            route = Rutas.Pokedex.ruta,
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face,
        )
    )

    var selectedItemIndex by rememberSaveable {
        androidx.compose.runtime.mutableIntStateOf(0)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()


    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent =
        {
            ModalDrawerSheet {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                                },
                        selected = index == selectedItemIndex,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }

                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon, contentDescription = item.title
                            )
                        },
                        badge = { item.badgeCount?.let { Text(text = item.badgeCount.toString()) } },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }) {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.nombre_app)
                    )
                        },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.apply { if (isClosed) open() else close() } } }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                })
        }) {
            Box(modifier = Modifier.padding(it)) {
                NavHost(
                    navController = navController,
                    startDestination = Rutas.Principal.ruta
                )
                {
                    composable(Rutas.Principal.ruta) {
                        Principal(navController, viewModelAuth)
                    }
                    composable (Rutas.Configuracion.ruta) {
                        Configuracion(navController)
                    }
                    composable(Rutas.Ayuda.ruta) {
                        Ayuda(navController)
                    }
                    composable(Rutas.AcercaDe.ruta) {
                        AcercaDe(navController)
                    }
                    composable(Rutas.Pokedex.ruta) {
                        Pokemon(viewModel, navController)
                    }
                    composable(Rutas.InicioSesion.ruta) {
                        InicioSesion(navController, viewModelAuth)
                    }
                    composable(Rutas.Registro.ruta) {
                        Registro(navController, viewModelAuth)
                    }
                    composable(Rutas.Tareas.ruta) {
                        Tareas(navController, tareasViewModel)
                    }
                }
            }
        }
    }






    LaunchedEffect(navBackStackEntry) {
        navBackStackEntry?.let { backStackEntry ->
            val currentRoute = backStackEntry.destination.route
            selectedItemIndex = items.indexOfFirst {
                it.route == currentRoute
            }.takeIf { it >= 0 } ?: 0
        }
    }

}


