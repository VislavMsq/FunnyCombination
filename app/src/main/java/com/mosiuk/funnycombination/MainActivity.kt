package com.mosiuk.funnycombination

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.mosiuk.funnycombination.data.AppDatabase
import com.mosiuk.funnycombination.data.HighScoreRepository
import com.mosiuk.funnycombination.navigation.Screen
// Импорты UI-компонентов. Предполагаем, что все они находятся в пакете 'ui'.
import com.mosiuk.funnycombination.ui.HighScoresScreen
import com.mosiuk.funnycombination.ui.GameOverScreen
import com.mosiuk.funnycombination.ui.GameScreen
import com.mosiuk.funnycombination.ui.MainMenuScreen
import com.mosiuk.funnycombination.ui.PrivacyPolicyScreen
import com.mosiuk.funnycombination.ui.SplashScreen
import com.mosiuk.funnycombination.ui.theme.FunnyCombinationTheme // Убедитесь, что FunnyCombinationTheme находится здесь
import com.mosiuk.funnycombination.viewmodel.HighScoreViewModel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- 1. Слой данных (база, dao, репозиторий, ViewModel) ---
        // Создание ViewModel вне Composable-функций
        // Этот ViewModel будет доступен для захвата в лямбдах setContent
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "high_scores_db"
        ).build()
        val highScoreDao = db.highScoreDao()
        val highScoreRepository = HighScoreRepository(highScoreDao)
        val highScoreViewModel = HighScoreViewModel(highScoreRepository)

        // --- 2. setContent: только ViewModel и навигация ---
        setContent {
            FunnyCombinationTheme {
                val navController = rememberNavController() // NavController создается внутри Composable-скоупа
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
                        GameScreen(
                            onGameOver = { score ->
                                // highScoreViewModel доступен здесь, так как он захватывается из внешнего onCreate скоупа
                                highScoreViewModel.saveHighScore(score)
                                navController.navigate(Screen.GameOver.createRoute(score))
                            }
                        )
                    }

                    composable(
                        route = Screen.GameOver.route,
                        arguments = listOf(navArgument("score") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val score = backStackEntry.arguments?.getInt("score") ?: 0
                        GameOverScreen(
                            score = score,
                            navController = navController,
                            highScoreViewModel = highScoreViewModel // ViewModel передается
                        )
                    }

                    composable(Screen.HighScores.route) {
                        HighScoresScreen(
                            navController = navController,
                            highScoreViewModel = highScoreViewModel // ViewModel передается
                        )
                    }

                    composable(Screen.PrivacyPolicy.route) {
                        PrivacyPolicyScreen(navController)
                    }
                }
            }
        }
    }
}