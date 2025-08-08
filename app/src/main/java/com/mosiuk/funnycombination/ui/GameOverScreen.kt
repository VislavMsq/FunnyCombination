package com.mosiuk.funnycombination.ui

import HighScoreViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mosiuk.funnycombination.navigation.Screen
import com.mosiuk.funnycombination.data.HighScoreDao
import kotlinx.coroutines.launch

@Composable
fun GameOverScreen(
    score: Int,
    navController: NavController,
    highScoreViewModel: HighScoreViewModel // <- ViewModel!
) {
    var isNewRecord by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(score) {
        coroutineScope.launch {
            isNewRecord = highScoreViewModel.isNewRecord(score)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Игра окончена! 😭",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ваш счет: $score",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 24.sp
            )
            if (isNewRecord) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Новый рекорд! 😎",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 22.sp
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.Game.route) {
                        popUpTo(Screen.MainMenu.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(text = "Играть снова", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.MainMenu.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(text = "Главное меню", fontSize = 20.sp)
            }
        }
    }
}
