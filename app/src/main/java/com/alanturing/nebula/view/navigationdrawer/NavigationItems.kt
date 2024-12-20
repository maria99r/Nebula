package com.alanturing.nebula.view.navigationdrawer

import androidx.compose.ui.graphics.vector.ImageVector

// Create Navigation Items Class to Select UnSelect items
data class NavigationItems(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: String
)