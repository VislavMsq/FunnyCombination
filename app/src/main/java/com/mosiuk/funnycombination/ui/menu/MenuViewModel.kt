package com.mosiuk.funnycombination.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.domain.usecase.GetBestScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для главного меню.
 * Обрабатывает действия пользователя и загружает лучший результат для отображения.
 */
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getBestScoreUseCase: GetBestScoreUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    private val _effect = Channel<MenuEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadBestScore()
    }

    /**
     * Обрабатывает события, приходящие от UI.
     */
    fun handleEvent(event: MenuEvent) {
        viewModelScope.launch {
            when (event) {
                MenuEvent.OnPlayClick -> _effect.send(MenuEffect.NavigateToGame)
                MenuEvent.OnHighScoresClick -> _effect.send(MenuEffect.NavigateToHighScores)
                MenuEvent.OnPrivacyPolicyClick -> _effect.send(MenuEffect.NavigateToPrivacyPolicy)
            }
        }
    }

    private fun loadBestScore() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getBestScoreUseCase()) {
                is Result.Success -> {
                    _state.update { it.copy(bestScore = result.data, isLoading = false) }
                }
                is Result.Error -> {
                    // Можно обработать ошибку, например, показать Toast
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}