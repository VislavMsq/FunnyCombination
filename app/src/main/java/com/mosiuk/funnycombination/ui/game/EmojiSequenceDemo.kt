package com.mosiuk.funnycombination.ui.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun EmojiSequenceDemo(
    sequence: List<String>,
    onSequenceShown: () -> Unit
) {
    val scales = remember(sequence) { sequence.map { Animatable(0f) } }

    LaunchedEffect(sequence) {
        if (sequence.isNotEmpty()) {
            sequence.indices.forEach { index ->
                delay(300)
                scales[index].animateTo(1f, animationSpec = tween(300))
                delay(400)
            }
            delay(500)
            onSequenceShown()
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sequence.forEachIndexed { index, emoji ->
            Text(
                text = emoji,
                fontSize = 48.sp,
                modifier = Modifier
                    .size(60.dp)
                    .scale(scales[index].value)
                    .padding(4.dp)
            )
        }
    }
}
