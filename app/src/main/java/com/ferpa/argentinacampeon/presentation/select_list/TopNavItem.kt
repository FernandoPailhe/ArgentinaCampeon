package com.ferpa.argentinacampeon.presentation.select_list

import androidx.compose.ui.graphics.vector.ImageVector

data class TopNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0,
    val hasNews: Boolean = false
)
