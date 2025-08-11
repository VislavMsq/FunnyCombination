package com.mosiuk.funnycombination.ui.menu

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
fun MainMenuScreen(
    navController: NavController,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                MenuEffect.NavigateToGame -> navController.navigate(Screen.Game.route)
                MenuEffect.NavigateToHighScores -> navController.navigate(Screen.HighScores.route)
                MenuEffect.NavigateToPrivacyPolicy -> navController.navigate(Screen.PrivacyPolicy.route)
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
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.best_score, state.bestScore),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = { viewModel.handleEvent(MenuEvent.OnPlayClick) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.play_game))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.handleEvent(MenuEvent.OnHighScoresClick) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.high_scores))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.handleEvent(MenuEvent.OnPrivacyPolicyClick) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.privacy_policy))
        }
    }
}
