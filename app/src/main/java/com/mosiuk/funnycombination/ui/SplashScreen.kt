package com.mosiuk.funnycombination.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    var textVisible by remember { mutableStateOf(false) }
    var shouldCallOnTimeout by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        textVisible = true
        delay(1000)
        shouldCallOnTimeout = true
        textVisible = false
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = textVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        ) {
            Text(
                text = "Funny Combination",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 36.sp
            )
        }
    }

    LaunchedEffect(shouldCallOnTimeout) {
        if (shouldCallOnTimeout) {
            delay(1000)
            onTimeout()
        }
    }
}