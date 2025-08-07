package com.mosiuk.funnycombination.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mosiuk.funnycombination.Screen
import com.mosiuk.funnycombination.ui.theme.FunnyCombinationTheme

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