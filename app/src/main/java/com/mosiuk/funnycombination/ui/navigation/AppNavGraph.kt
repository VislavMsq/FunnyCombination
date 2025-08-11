package com.mosiuk.funnycombination.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mosiuk.funnycombination.ui.game.GameScreen
import com.mosiuk.funnycombination.ui.gameover.GameOverScreen
import com.mosiuk.funnycombination.ui.highscores.HighScoresScreen
import com.mosiuk.funnycombination.ui.menu.MainMenuScreen
import com.mosiuk.funnycombination.ui.privacy.PrivacyPolicyScreen
import com.mosiuk.funnycombination.ui.splash.SplashScreen

/**
 * Определяет граф навигации для всего приложения.
 * Связывает маршруты из [Screen] с конкретными Composable-функциями экранов.
 */
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.MainMenu.route) {
            MainMenuScreen(navController = navController)
        }
        composable(Screen.Game.route) {
            GameScreen(navController = navController)
        }
        composable(
            route = Screen.GameOver.route,
            arguments = listOf(navArgument("score") { type = NavType.IntType })
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            GameOverScreen(score = score, navController = navController)
        }
        composable(Screen.HighScores.route) {
            HighScoresScreen(navController = navController)
        }
        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(navController = navController)
        }
    }
}