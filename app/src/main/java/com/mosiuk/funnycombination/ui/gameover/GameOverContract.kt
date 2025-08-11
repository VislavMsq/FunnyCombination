package com.mosiuk.funnycombination.ui.gameover

interface GameOverEvent {
    object OnPlayAgainClick : GameOverEvent
    object OnMainMenuClick : GameOverEvent
}

data class GameOverState(
    val finalScore: Int = 0,
    val bestScore: Int = 0,
    val isNewBestScore: Boolean = false,
    val isLoading: Boolean = true
)

sealed interface GameOverEffect {
    object NavigateToGame : GameOverEffect
    object NavigateToMainMenu : GameOverEffect
}
