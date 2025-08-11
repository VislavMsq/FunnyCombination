package com.mosiuk.funnycombination.ui.menu

interface MenuEvent {
    object OnPlayClick : MenuEvent
    object OnHighScoresClick : MenuEvent
    object OnPrivacyPolicyClick : MenuEvent
}

data class MenuState(
    val bestScore: Int = 0,
    val isLoading: Boolean = true
)

sealed interface MenuEffect {
    object NavigateToGame : MenuEffect
    object NavigateToHighScores : MenuEffect
    object NavigateToPrivacyPolicy : MenuEffect
}
