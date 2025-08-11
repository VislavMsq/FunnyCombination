package com.mosiuk.funnycombination.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для игрового экрана.
 * Управляет состоянием игры, обрабатывает ввод пользователя и взаимодействует с [GameEngine].
 */
@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameEngine: GameEngine
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private val _effect = Channel<GameEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        startGame()
    }

    fun handleEvent(event: GameEvent) {
        when (event) {
            is GameEvent.OnEmojiClick -> onEmojiClicked(event.emoji)
            is GameEvent.OnSequenceShown -> onSequenceShown()
        }
    }

    private fun startGame() {
        _state.update { GameState() } // Сброс состояния на начальное
        startNewRound()
    }

    private fun startNewRound() {
        viewModelScope.launch {
            val newSequence = gameEngine.createNewSequence(_state.value.level)
            _state.update {
                it.copy(
                    sequence = newSequence,
                    userInput = emptyList(),
                    isShowingSequence = true,
                    message = "Remember the sequence!",
                    showMistake = false
                )
            }
        }
    }

    private fun onSequenceShown() {
        _state.update {
            it.copy(
                isShowingSequence = false,
                message = "Now, your turn!"
            )
        }
    }

    private fun onEmojiClicked(emoji: String) {
        if (_state.value.isShowingSequence || _state.value.showMistake) return

        val newUserInput = _state.value.userInput + emoji
        _state.update { it.copy(userInput = newUserInput) }

        // Если длина ввода совпала с длиной последовательности, проверяем
        if (newUserInput.size == _state.value.sequence.size) {
            validatePlayerInput()
        }
    }

    private fun validatePlayerInput() {
        viewModelScope.launch {
            val isValid = gameEngine.validateInput(_state.value.userInput, _state.value.sequence)
            if (isValid) {
                // Успех
                _state.update {
                    it.copy(
                        message = "Correct!",
                        level = it.level + 1
                    )
                }
                delay(SUCCESS_DELAY_MS)
                startNewRound()
            } else {
                // Ошибка
                _state.update {
                    it.copy(
                        message = "Wrong sequence!",
                        showMistake = true
                    )
                }
                delay(FAILURE_DELAY_MS)
                _effect.send(GameEffect.NavigateToGameOver(_state.value.level - 1))
            }
        }
    }

    companion object {
        private const val SUCCESS_DELAY_MS = 1000L
        private const val FAILURE_DELAY_MS = 2000L
    }
}