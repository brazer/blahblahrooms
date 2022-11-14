package com.softeq.blahblahrooms.presentation

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun Context.pxToDp(px: Int): Dp {
    return (px / resources.displayMetrics.density).toInt().dp
}