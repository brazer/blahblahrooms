package com.softeq.blahblahrooms.presentation.route

import androidx.compose.ui.graphics.painter.Painter

data class NavigationBottomItem(
    val icon: Painter,
    val label: String,
    val route: String
)