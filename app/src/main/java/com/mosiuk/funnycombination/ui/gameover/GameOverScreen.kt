package com.mosiuk.funnycombination.ui.gameover

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mosiuk.funnycombination.R
import com.mosiuk.funnycombination.ui.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GameOverScreen(
    score: Int,
    navController: NavController,
    viewModel: GameOverViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                GameOverEffect.NavigateToGame -> {
                    navController.navigate(Screen.Game.route) {
                        popUpTo(Screen.MainMenu.route)
                    }
                }
                GameOverEffect.NavigateToMainMenu -> {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.MainMenu.route) { inclusive = true }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.game_over),
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        if (state.isNewBestScore) {
            Text(
                text = stringResource(R.string.new_best_score),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = stringResource(R.string.your_score, state.finalScore),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.best_score, state.bestScore),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = { viewModel.handleEvent(GameOverEvent.OnPlayAgainClick) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.play_again))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.handleEvent(GameOverEvent.OnMainMenuClick) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.main_menu))
        }
    }
}
