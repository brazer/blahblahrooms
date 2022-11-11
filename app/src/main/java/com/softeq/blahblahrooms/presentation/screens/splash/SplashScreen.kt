package com.softeq.blahblahrooms.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.softeq.blahblahrooms.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    delay: Long = 2000L
) {
    val isVisible = remember {
        mutableStateOf(true)
    }
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = null, block = {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        )
        delay(delay)
        isVisible.value = false
    })
    if (isVisible.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "splash_screen",
                modifier = Modifier
                    .size(400.dp)
                    .scale(scale.value)
            )
        }
    }
}