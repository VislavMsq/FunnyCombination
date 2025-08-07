package com.mosiuk.funnycombination.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
fun EmojiSequenceDemo(
    sequence: List<String>,
    onShowEnd: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(-1) }
    var showEmoji by remember { mutableStateOf(true) }


    LaunchedEffect(sequence) {
        for (i in sequence.indices) {
            showEmoji = true
            currentIndex = i
            delay(800)
            showEmoji = false
            delay(300)
        }
        currentIndex = -1
        onShowEnd()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (currentIndex != -1 && showEmoji) {
            Text(sequence[currentIndex], fontSize = 50.sp)
        }
    }
}