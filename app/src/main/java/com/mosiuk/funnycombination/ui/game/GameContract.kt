package com.mosiuk.funnycombination.ui.game

interface GameEvent {
    data class OnEmojiClick(val emoji: String) : GameEvent
    object OnSequenceShown : GameEvent
}

data class GameState(
    val level: Int = 1,
    val sequence: List<String> = emptyList(),
    val userInput: List<String> = emptyList(),
    val isShowingSequence: Boolean = false,
    val message: String? = null,
    val showMistake: Boolean = false
)

sealed interface GameEffect {
    data class NavigateToGameOver(val score: Int) : GameEffect
}
