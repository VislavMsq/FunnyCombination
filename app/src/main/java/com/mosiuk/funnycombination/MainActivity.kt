package com.mosiuk.funnycombination

import HighScoreViewModel
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.mosiuk.funnycombination.ui.modern.ModernGameOverScreen
import com.mosiuk.funnycombination.ui.modern.ModernGameScreen
import com.mosiuk.funnycombination.ui.modern.ModernHighScoresScreen
import com.mosiuk.funnycombination.ui.modern.ModernMainMenuScreen
import com.mosiuk.funnycombination.ui.modern.ModernSplashScreen
import com.mosiuk.funnycombination.ui.theme.FunnyCombinationTheme // Убедитесь, что FunnyCombinationTheme находится здесь
import com.mosiuk.funnycombination.ui.viewmodel.HighScoreViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        enableEdgeToEdge()
    }

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "high_scores_db"
        ).build()
        val highScoreDao = db.highScoreDao()
        val highScoreRepository = HighScoreRepository(highScoreDao)
        val highScoreViewModel = HighScoreViewModel(highScoreRepository)

        setContent {
            FunnyCombinationTheme {
                val navController = rememberNavController() // NavController создается внутри Composable-скоупа
                NavHost(navController, startDestination = Screen.Splash.route) {

                    composable(Screen.Splash.route) {
                        ModernSplashScreen(onTimeout = {
                            navController.navigate(Screen.MainMenu.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        })
                    }

                    composable(Screen.MainMenu.route) {
                        ModernMainMenuScreen(navController)
                    }

                    composable(Screen.Game.route) {
                        ModernGameScreen(
                            onGameOver = { score ->
                                navController.navigate(Screen.GameOver.createRoute(score))
                            }
                        )
                    }

                    // GameOver
                    composable(
                        route = Screen.GameOver.route,
                        arguments = listOf(navArgument("score") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val score = backStackEntry.arguments?.getInt("score") ?: 0
                        val ctx = LocalContext.current
                        val hsVm: HighScoreViewModel = viewModel(factory = HighScoreViewModelFactory(ctx))
                        ModernGameOverScreen(score = score, navController = navController, highScoreViewModel = hsVm)
                    }


                    // HighScores
                    composable(Screen.HighScores.route) {
                        val ctx = LocalContext.current
                        val hsVm: HighScoreViewModel = viewModel(factory = HighScoreViewModelFactory(ctx))
                        ModernHighScoresScreen(navController = navController, highScoreViewModel = hsVm)
                    }


                    composable(Screen.PrivacyPolicy.route) {
                        PrivacyPolicyScreen(navController) // этот можешь оставить старый как есть
                    }
                }

            }
        }
    }

}