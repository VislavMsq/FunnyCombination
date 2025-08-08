// =============================
// FunnyCombination – Modern UI
// Jetpack Compose Material 3
// Drop-in replacement screens that keep your ViewModels & navigation
// =============================

package com.mosiuk.funnycombination.ui.modern

import HighScoreViewModel
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.R
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mosiuk.funnycombination.navigation.Screen
import com.mosiuk.funnycombination.viewmodel.GameViewModel

// -----------------------------
// Shared UI kit
// -----------------------------

@Composable
fun GradientBackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                    )
                )
            ),
        content = content
    )
}

@Composable
fun LevelChip(level: Int, modifier: Modifier = Modifier) {
    AssistChip(
        onClick = {},
        label = { Text("Уровень $level") },
        modifier = modifier,
        leadingIcon = {
            Box(
                Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    )
}

@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), content = {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            content()
        })
    }
}

@Composable
fun GlowingEmojiButton(emoji: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val glow by remember { mutableStateOf(0.12f) }
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier
            .height(56.dp),
    ) { Text(emoji, fontSize = 28.sp) }
}

// -----------------------------
// Modern Splash
// -----------------------------

@Composable
fun ModernSplashScreen(onTimeout: () -> Unit) {
    // тайминг
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1200)
        onTimeout()
    }

    // фон: мягкий радиальный градиент
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.20f)
                    ),
                    center = Offset.Infinite,  // центр станет по середине бокса
                    radius = 1400f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // лёгкая анимация появления
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 6 })
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Funny Combination",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Проверь свою память",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.alpha(0.9f)
                )
            }
        }
    }
}


// -----------------------------
// Modern Main Menu
// -----------------------------

@Composable
fun ModernMainMenuScreen(navController: NavController) {
    // фон: комбинируем вертикальный + радиальный градиенты
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.30f),
                    )
                )
            )
    ) {
        val fruits = listOf("🍎", "🍌", "🍒", "🍇", "🍊")
        val infinite = rememberInfiniteTransition(label = "float")
        val cfg = LocalConfiguration.current
        val screenH = cfg.screenHeightDp.dp.value

        fruits.forEachIndexed { i, emoji ->
            val bounceY by infinite.animateFloat(
                initialValue = 0f,
                targetValue = screenH,
                animationSpec = infiniteRepeatable(
                    animation = tween(4000 + i * 300, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "y$i"
            )
            val alpha by infinite.animateFloat(
                initialValue = 0.12f, targetValue = 0.28f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2400 + i * 180, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "a$i"
            )
            val baseX = when (i) { 0 -> -120.dp; 1 -> -40.dp; 2 -> 0.dp; 3 -> 60.dp; else -> 130.dp }
            Text(
                text = emoji,
                fontSize = 48.sp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(x = baseX, y = bounceY.dp)
                    .alpha(alpha)
            )
        }


        // Контент
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Заголовок по центру с анимацией
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -50 })
            ) {
                Text(
                    text = "Funny Combination",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Аркада на внимательность",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.alpha(0.85f)
            )

            Spacer(Modifier.height(40.dp))
            Button(
                onClick = { navController.navigate(Screen.Game.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Играть")
            }
            Spacer(Modifier.height(12.dp))
            FilledTonalButton(
                onClick = { navController.navigate(Screen.HighScores.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.BarChart, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Рекорды")
            }
            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = { navController.navigate(Screen.PrivacyPolicy.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PrivacyTip, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Пользовательское соглашение")
            }
        }
    }
}


// -----------------------------
// Modern Game
// -----------------------------

//@OptIn(ExperimentalAnimationApi::class)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModernGameScreen(
    viewModel: GameViewModel = viewModel(),
    onGameOver: (score: Int) -> Unit
) {
    val safeOnGameOver = rememberUpdatedState(onGameOver)
    var ended by remember { mutableStateOf(false) }

    val sequence by remember { derivedStateOf { viewModel.sequence } }
    val playerInput by remember { derivedStateOf { viewModel.playerInput } }
    val isShowingSequence by remember { derivedStateOf { viewModel.isShowingSequence } }
    val readyForNextRound by remember { derivedStateOf { viewModel.readyForNextRound } }
    val currentMessage by remember { derivedStateOf { viewModel.currentMessage } }
    val showCorrectRow by remember { derivedStateOf { viewModel.showCorrectRow } }
    val showMistake by remember { derivedStateOf { viewModel.showMistake } }
    val mistakeInput by remember { derivedStateOf { viewModel.mistakeInput } }
    val level = sequence.size

    LaunchedEffect(Unit) { viewModel.startGame() }
    LaunchedEffect(readyForNextRound) { if (readyForNextRound) viewModel.nextLevel() }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Top bar area
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                LevelChip(level)
                Text("Счёт: ${level - 1}", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(Modifier.height(12.dp))

            // Central card with sequence/status
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedContent(
                        targetState = when {
                            showCorrectRow || showMistake -> "compare"
                            isShowingSequence -> "sequence"
                            else -> "input"
                        },
                        transitionSpec = {
                            fadeIn(tween(1)) togetherWith fadeOut(tween(1)) using SizeTransform(clip = false)
                        },
                        label = "content"
                    ) { state ->
                        when (state) {
                            "compare" -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        currentMessage ?: "",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if (showMistake) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text("Правильный ответ:")
                                    EmojiBlock(sequence)
                                    Spacer(Modifier.height(8.dp))
                                    Text("Твой ответ:")
                                    EmojiBlock(
                                        if (showCorrectRow) playerInput else mistakeInput,
                                        large = true,
                                        tintedError = showMistake
                                    )
                                }
                            }

                            "sequence" -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Запоминай…", style = MaterialTheme.typography.titleMedium)
                                    Spacer(Modifier.height(8.dp))
                                    SequenceTicker(sequence) { viewModel.onSequenceShown() }
                                }
                            }

                            else -> {
                                if (currentMessage != null) {
                                    Text(
                                        currentMessage!!,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    EmojiBlock(playerInput, large = true)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Emoji keypad
            if (currentMessage == null && !isShowingSequence && !readyForNextRound && !showCorrectRow && !showMistake) {
                SectionCard(title = "Выбери эмодзи по порядку") {
                    val keypad = listOf("🍎", "🍌", "🍇", "🍒", "🍊")
                    FlowRow(
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        maxItemsInEachRow = 3,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        keypad.forEach { e ->
                            GlowingEmojiButton(
                                emoji = e,
                                onClick = {
                                    if (!ended) {
                                        viewModel.onEmojiClick(e) { score ->
                                            if (!ended) {
                                                ended = true
                                                safeOnGameOver.value(score)
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmojiBlock(list: List<String>, large: Boolean = false, tintedError: Boolean = false) {
    val fs = if (large) 40.sp else 28.sp
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        list.forEach {
            Text(
                text = it,
                fontSize = fs,
                color = if (tintedError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}


@Composable
fun SequenceTicker(sequence: List<String>, onEnd: () -> Unit) {
    var index by remember { mutableStateOf(-1) }
    var show by remember { mutableStateOf(true) }

    LaunchedEffect(sequence) {
        for (i in sequence.indices) {
            show = true
            index = i
            kotlinx.coroutines.delay(700)
            show = false
            kotlinx.coroutines.delay(250)
        }
        index = -1
        onEnd()
    }

    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        if (index != -1 && show) {
            val alpha by animateFloatAsState(
                targetValue = if (show) 1f else 0f, label = "alpha"
            )
            Text(sequence[index], fontSize = 54.sp, modifier = Modifier.alpha(alpha))
        }
    }
}

// -----------------------------
// Modern Game Over
// -----------------------------

@Composable
fun ModernGameOverScreen(
    score: Int,
    navController: NavController,
    highScoreViewModel: HighScoreViewModel
) {
    var isNewRecord by remember { mutableStateOf(false) }
    LaunchedEffect(score) {
        // проверяем и сохраняем рекорд безопасно на IO
        isNewRecord = highScoreViewModel.isNewRecord(score)
        highScoreViewModel.saveHighScore(score)
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Игра окончена!", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(12.dp))
            Text("Ваш счёт: $score", style = MaterialTheme.typography.titleLarge)
            if (isNewRecord) {
                Spacer(Modifier.height(8.dp))
                AssistChip(onClick = {}, label = { Text("Новый рекорд!") })
            }
            Spacer(Modifier.height(32.dp))
            Button(onClick = {
                navController.navigate(Screen.Game.route) {
                    popUpTo(Screen.MainMenu.route) { inclusive = true }
                }
            }, modifier = Modifier.fillMaxWidth()) { Text("Играть снова") }
            Spacer(Modifier.height(12.dp))
            OutlinedButton(onClick = {
                navController.navigate(Screen.MainMenu.route) {
                    popUpTo(Screen.MainMenu.route) { inclusive = true }
                }
            }, modifier = Modifier.fillMaxWidth()) { Text("Главное меню") }
        }
    }
}

// -----------------------------
// Modern High Scores
// -----------------------------

@Composable
fun ModernHighScoresScreen(
    navController: NavController,
    highScoreViewModel: HighScoreViewModel
) {
    val scores by highScoreViewModel.scores.collectAsState()
    LaunchedEffect(Unit) { highScoreViewModel.loadScores() }

    // форматируем "2025-08-08" -> "8 авг 2025"
    fun formatDate(src: String): String = try {
        val inFmt = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val outFmt = java.text.SimpleDateFormat("d MMM yyyy", java.util.Locale.getDefault())
        outFmt.format(inFmt.parse(src)!!)
    } catch (_: Throwable) { src }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Рекорды",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            if (scores.isEmpty()) {
                ElevatedCard(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Пока нет рекордов", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        Text("Сыграй первую партию, чтобы установить рекорд")
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(scores.indices.toList()) { idx ->
                        val r = scores[idx]
                        ElevatedCard(Modifier.fillMaxWidth()) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Левая часть: бейдж места + дата
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when (idx) {
                                                    0 -> MaterialTheme.colorScheme.primary
                                                    1 -> MaterialTheme.colorScheme.secondary
                                                    2 -> MaterialTheme.colorScheme.tertiary
                                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                                }
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            (idx + 1).toString(),
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            formatDate(r.date),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            "Топ ${idx + 1}",
                                            modifier = Modifier.alpha(0.7f)
                                        )
                                    }
                                }

                                // Правая часть: компактный бейдж очков
                                AssistChip(
                                    onClick = {},
                                    label = { Text("${r.score} очк.") }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.MainMenu.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Главное меню") }
        }
    }
}

