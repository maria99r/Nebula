package com.alanturing.nebula.view

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alanturing.nebula.R
import com.alanturing.nebula.model.Rutas
import com.alanturing.nebula.model.activities.ActivityResponse
import com.alanturing.nebula.model.activities.ParticipationResponse
import com.alanturing.nebula.viewModel.authentication.ViewModelActivities
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class TabItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividades(navController: NavHostController, viewModel: ViewModelActivities ) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val accessToken by viewModel.accessToken.collectAsState()
    val allActivities by viewModel.allActivities.collectAsState()
    val userActivities by viewModel.userActivities.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        TabItem(stringResource(id = R.string.todas_act), Icons.Default.DateRange),
        TabItem(stringResource(id = R.string.mis_activi), Icons.Default.Star)
    )

    LaunchedEffect(accessToken) {
        if (accessToken.isNotEmpty()) {
            viewModel.getAllActivities()
            viewModel.getUserActivities()
        } else {
            delay(500)
            if (accessToken.isEmpty()) {
                Log.i("token de actividades", "Token vacío, redirigiendo a inicio de sesion")
                navController.navigate(Rutas.InicioSesion.ruta)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCredentials()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {

            TopAppBar(title = { Text(stringResource(id = R.string.activity)) })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = { Text(tab.title) },
                        icon = { Icon(tab.icon, contentDescription = null) }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> ActivitiesList(
                    allActivities, join = { activity ->
                    viewModel.createParticipation(activity.id)
                    scope.launch {
                        viewModel.getUserActivities()
                        snackbarHostState.showSnackbar(
                            "Te has apuntado a ${activity.name}")
                    }
                } , userActivity = userActivities)


                1 -> ActivitiesList(userActivities, delete = { participation ->
                    viewModel.deleteParticipation(participation.id)
                    scope.launch {
                        viewModel.getUserActivities()
                        snackbarHostState.showSnackbar(
                            "Te has borrado de ${participation.name}")
                    }
                }, isUserActivity = true)
            }
        }
    }
}

@Composable
fun ActivitiesList(
    activities: List<ActivityResponse>,
    join: (ActivityResponse) -> Unit = {},
    delete: (ActivityResponse) -> Unit = {},
    isUserActivity: Boolean = false,
    userActivity: List<ActivityResponse> = emptyList()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Log.i("usrActivities", userActivity.toString())

    if (activities.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isUserActivity) Text(stringResource(id = R.string.noactivity))
            else CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(activities, key = { it.id }) { activity ->
                var visible by remember { mutableStateOf(true) }

                    AnimatedVisibility(
                        visible = visible,
                        exit = slideOutVertically(animationSpec = tween(durationMillis = 1500)) +
                                shrinkVertically(animationSpec = tween(durationMillis = 1500)) +
                                fadeOut(animationSpec = tween(durationMillis = 1500)),
                                modifier = Modifier.animateContentSize()
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .animateContentSize(),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(activity.name, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(activity.description)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Lugar: ${activity.place}")

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(onClick = {
                                        if (isUserActivity) {
                                            visible = false
                                            coroutineScope.launch {
                                                delay(1500)
                                                delete(activity)
                                            }
                                        } else {
                                            join(activity)
                                        }
                                    }) {
                                        Icon(
                                            if (isUserActivity) Icons.Default.Delete
                                            else {
                                                if (userActivity.contains(activity)) {
                                                    Icons.Default.Favorite
                                                } else
                                                    Icons.Default.FavoriteBorder
                                            },
                                            contentDescription = if (isUserActivity) "Borrarse" else "Apuntarse"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

        }
    }
}
