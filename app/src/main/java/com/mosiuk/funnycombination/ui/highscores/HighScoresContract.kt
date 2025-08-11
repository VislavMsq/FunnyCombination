package com.mosiuk.funnycombination.ui.highscores

import com.mosiuk.funnycombination.domain.model.HighScore

interface HighScoresEvent

data class HighScoresState(
    val scores: List<HighScore> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

sealed interface HighScoresEffect
