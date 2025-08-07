package com.mosiuk.funnycombination.navigation

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