package com.mosiuk.funnycombination.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mosiuk.funnycombination.R
import com.mosiuk.funnycombination.ui.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val emojiPool = listOf("😂", "😍", "😭", "🤔", "😎", "😡", "🤯", "😱", "😇", "🥳")

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is GameEffect.NavigateToGameOver -> {
                    navController.navigate(Screen.GameOver.createRoute(effect.score)) {
                        popUpTo(Screen.Game.route) { inclusive = true }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Info: Level and Message
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.level, state.level),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            state.message?.let {
                Text(text = it, style = MaterialTheme.typography.bodyLarge)
            }
        }

        // Middle: Sequence display or user input display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isShowingSequence) {
                EmojiSequenceDemo(
                    sequence = state.sequence,
                    onSequenceShown = { viewModel.handleEvent(GameEvent.OnSequenceShown) }
                )
            } else {
                UserInputDisplay(
                    userInput = state.userInput,
                    correctSequence = state.sequence,
                    showMistake = state.showMistake
                )
            }
        }

        // Bottom: Emoji Keyboard
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 70.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(emojiPool) { emoji ->
                Card(
                    modifier = Modifier
                        .size(70.dp)
                        .clickable(enabled = !state.isShowingSequence && !state.showMistake) {
                            viewModel.handleEvent(GameEvent.OnEmojiClick(emoji))
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = emoji, fontSize = 32.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun UserInputDisplay(
    userInput: List<String>,
    correctSequence: List<String>,
    showMistake: Boolean
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        val displayList = if (showMistake) correctSequence else userInput
        displayList.forEachIndexed { index, emoji ->
            val isMistake = showMistake && userInput.getOrNull(index) != emoji
            val backgroundColor = if (isMistake) Color.Red.copy(alpha = 0.5f) else Color.Transparent
            Text(
                text = emoji,
                fontSize = 48.sp,
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(4.dp)
            )
        }
    }
}
