package com.mosiuk.funnycombination.ui.gameover

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.domain.usecase.GetBestScoreUseCase
import com.mosiuk.funnycombination.domain.usecase.InsertHighScoreIfBestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана "Game Over".
 * Получает финальный счет, сохраняет его (если он лучший) и отображает информацию.
 */
@HiltViewModel
class GameOverViewModel @Inject constructor(
    private val insertHighScoreIfBestUseCase: InsertHighScoreIfBestUseCase,
    private val getBestScoreUseCase: GetBestScoreUseCase,
    savedStateHandle: SavedStateHandle // Для получения аргументов навигации
) : ViewModel() {

    private val finalScore: Int = savedStateHandle.get<Int>("score") ?: 0

    private val _state = MutableStateFlow(GameOverState(finalScore = finalScore))
    val state = _state.asStateFlow()

    private val _effect = Channel<GameOverEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        processScore()
    }

    fun handleEvent(event: GameOverEvent) {
        viewModelScope.launch {
            when (event) {
                GameOverEvent.OnPlayAgainClick -> _effect.send(GameOverEffect.NavigateToGame)
                GameOverEvent.OnMainMenuClick -> _effect.send(GameOverEffect.NavigateToMainMenu)
            }
        }
    }

    private fun processScore() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val oldBestScoreResult = getBestScoreUseCase()
            val oldBestScore = if (oldBestScoreResult is Result.Success) oldBestScoreResult.data else 0

            // Пытаемся вставить новый счет
            insertHighScoreIfBestUseCase(finalScore)

            // Получаем актуальный лучший счет (он мог измениться)
            val newBestScoreResult = getBestScoreUseCase()
            val newBestScore = if (newBestScoreResult is Result.Success) newBestScoreResult.data else 0

            _state.update {
                it.copy(
                    bestScore = newBestScore,
                    isNewBestScore = finalScore > oldBestScore && finalScore > 0,
                    isLoading = false
                )
            }
        }
    }
}