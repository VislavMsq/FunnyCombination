package com.mosiuk.funnycombination.ui.navigation

/**
 * Sealed-класс, определяющий все экраны в приложении и их маршруты.
 * Используется для типизированной и безопасной навигации.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object MainMenu : Screen("main_menu")
    object Game : Screen("game")
    object GameOver : Screen("game_over/{score}") {
        fun createRoute(score: Int) = "game_over/$score"
    }
    object HighScores : Screen("high_scores")
    object PrivacyPolicy : Screen("privacy_policy")
}