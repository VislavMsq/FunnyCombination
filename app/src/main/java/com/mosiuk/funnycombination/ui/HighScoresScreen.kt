package com.mosiuk.funnycombination.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mosiuk.funnycombination.viewmodel.HighScoreViewModel

@Composable
fun HighScoresScreen(
    navController: NavController,
    highScoreViewModel: HighScoreViewModel
) {
    val scores by highScoreViewModel.scores.collectAsState()

    // Запускать загрузку только один раз при появлении
    LaunchedEffect(Unit) {
        highScoreViewModel.loadScores()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Рекорды", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Дата", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Text("Очки", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(Modifier.height(8.dp))

        if (scores.isEmpty()) {
            Text("Пока нет рекордов")
        } else {
            scores.forEach { record ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(record.date)
                    Text(record.score.toString())
                }
                Spacer(Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navController.navigate("main_menu") {
                    popUpTo("main_menu") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Главное меню", fontSize = 20.sp)
        }
    }
}
