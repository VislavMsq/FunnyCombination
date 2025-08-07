package com.mosiuk.funnycombination.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mosiuk.funnycombination.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    onGameOver: (score: Int) -> Unit
) {
    val sequence by remember { derivedStateOf { viewModel.sequence } }
    val playerInput by remember { derivedStateOf { viewModel.playerInput } }
    val isShowingSequence by remember { derivedStateOf { viewModel.isShowingSequence } }
    val readyForNextRound by remember { derivedStateOf { viewModel.readyForNextRound } }
    val currentMessage by remember { derivedStateOf { viewModel.currentMessage } }
    val showCorrectRow by remember { derivedStateOf { viewModel.showCorrectRow } }
    val showMistake by remember { derivedStateOf { viewModel.showMistake } }
    val mistakeInput by remember { derivedStateOf { viewModel.mistakeInput } }

    val emojiPool = listOf("🍎", "🍌", "🍇", "🍒", "🍊")
    val firstRow = emojiPool.take(3)
    val secondRow = emojiPool.drop(3)
    val level = sequence.size

    // Запускаем startGame при первом показе экрана
    LaunchedEffect(Unit) {
        viewModel.startGame()
    }

    // Следующий уровень после успеха
    LaunchedEffect(readyForNextRound) {
        if (readyForNextRound) {
            viewModel.nextLevel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Уровень $level", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        // Сравнение: успех или ошибка
        if (showCorrectRow || showMistake) {
            Text(currentMessage ?: "", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text("Правильный ответ:", style = MaterialTheme.typography.bodyLarge)
            Row {
                sequence.forEach { emoji ->
                    Text(
                        emoji,
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("Твой ответ:", style = MaterialTheme.typography.bodyLarge)
            Row {
                val inputToDisplay = if (showCorrectRow) playerInput else mistakeInput
                inputToDisplay.forEach { emoji ->
                    Text(
                        emoji,
                        fontSize = 40.sp,
                        color = if (showMistake) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        // Демонстрация последовательности
        else if (isShowingSequence) {
            EmojiSequenceDemo(
                sequence = sequence,
                onShowEnd = { viewModel.onSequenceShown() }
            )
        }
        // Ввод игрока
        else {
            if (currentMessage != null) {
                Text(
                    text = currentMessage!!,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Row {
                    playerInput.forEach { emoji ->
                        Text(emoji, fontSize = 40.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (currentMessage == null && !isShowingSequence && !readyForNextRound && !showCorrectRow && !showMistake) {
                // Первый ряд кнопок
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    firstRow.forEach { emoji ->
                        Button(
                            onClick = { viewModel.onEmojiClick(emoji, onGameOver) },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(emoji, fontSize = 28.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                // Второй ряд кнопок
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    secondRow.forEach { emoji ->
                        Button(
                            onClick = { viewModel.onEmojiClick(emoji, onGameOver) },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(emoji, fontSize = 28.sp)
                        }
                    }
                }
            }
        }
    }
}
