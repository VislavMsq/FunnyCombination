package com.mosiuk.funnycombination

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mosiuk.funnycombination.ui.theme.FunnyCombinationTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FunnyCombinationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Splash.route) {
                    composable(Screen.Splash.route) {
                        SplashScreen(onTimeout = {
                            navController.navigate(Screen.MainMenu.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        })
                    }
                    composable(Screen.MainMenu.route) {
                        MainMenuScreen(navController = navController)
                    }
                    composable(Screen.Game.route) {
                        GameScreen(onGameOver = { score ->
                            val date =
                                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            HighScoreRepository.add(HighScore(date, score))

                            navController.navigate(Screen.GameOver.createRoute(score))
                        })
                    }
                    composable(
                        route = Screen.GameOver.route,
                        arguments = listOf(navArgument("score") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val score = backStackEntry.arguments?.getInt("score") ?: 0
                        GameOverScreen(score = score, navController = navController)
                    }
                    composable(Screen.HighScores.route) {
                        HighScoresScreen()
                    }
                    composable(Screen.PrivacyPolicy.route) {
                        PrivacyPolicyScreen()
                    }
                }

            }
        }

    }
}

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


@Composable
fun GameScreen(
    onGameOver: (score: Int) -> Unit
) {
    val emojiPool = listOf("🍎", "🍌", "🍇", "🍒", "🍊")
    val firstRow = emojiPool.take(3)
    val secondRow = emojiPool.takeLast(2)

    var sequence by remember { mutableStateOf(listOf(emojiPool.random())) }
    var playerInput by remember { mutableStateOf(listOf<String>()) }
    var isShowingSequence by remember { mutableStateOf(true) }
    var readyForNextRound by remember { mutableStateOf(false) }

    val level = sequence.size

    // Задержка между раундами — чтобы показать полный ввод игрока
    LaunchedEffect(readyForNextRound) {
        if (readyForNextRound) {
            kotlinx.coroutines.delay(600)
            sequence = sequence + emojiPool.random()
            isShowingSequence = true
            playerInput = listOf()
            readyForNextRound = false
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
        if (isShowingSequence) {
            EmojiSequenceDemo(
                sequence = sequence,
                onShowEnd = {
                    isShowingSequence = false
                    playerInput = listOf()
                }
            )
        } else {
            Row {
                playerInput.forEach { emoji ->
                    Text(emoji, fontSize = 40.sp)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Первый ряд кнопок (3)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                firstRow.forEach { emoji ->
                    Button(
                        onClick = {
                            if (!readyForNextRound) { // Не реагируем на клики во время задержки!
                                playerInput = playerInput + emoji
                                val mistake = playerInput.zip(sequence).any { (a, b) -> a != b }
                                if (mistake) {
                                    onGameOver(sequence.size - 1)
                                } else if (playerInput.size == sequence.size) {
                                    readyForNextRound = true
                                }
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(emoji, fontSize = 28.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Второй ряд кнопок (2)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                secondRow.forEach { emoji ->
                    Button(
                        onClick = {
                            if (!readyForNextRound) {
                                playerInput = playerInput + emoji
                                val mistake = playerInput.zip(sequence).any { (a, b) -> a != b }
                                if (mistake) {
                                    onGameOver(sequence.size - 1)
                                } else if (playerInput.size == sequence.size) {
                                    readyForNextRound = true
                                }
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(emoji, fontSize = 28.sp)
                    }
                }
            }
        }
    }
}


@Composable
fun PrivacyPolicyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Пользовательское соглашение")

    }
}

@Composable
fun GameOverScreen(score: Int, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Игра окончена! Ваш счёт: $score")
    }
}

@Composable
fun HighScoresScreen() {
    val score = HighScoreRepository.scores

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text("Рекорды", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        if (score.isEmpty()) {
            Text("Пока нет рекордов")
        } else {
            score.forEach { record ->
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(record.date)
                    Text(record.score.toString())
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Funny Combination", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun MainMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(Screen.Game.route) }) {
            Text("Играть")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.HighScores.route) }) {
            Text("Рекорды")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.PrivacyPolicy.route) }) {
            Text("Пользовательское соглашение")
        }
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = { navController.navigate(Screen.Splash.route) }) {
            Text("Выйти")
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name! Добро пожаловать в FunnyCombination!",
            modifier = modifier
        )
    }

    //    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        FunnyCombinationTheme {
            Greeting("Android")
        }
    }
}