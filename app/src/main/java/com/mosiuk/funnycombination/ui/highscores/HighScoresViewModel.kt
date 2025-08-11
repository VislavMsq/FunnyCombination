package com.mosiuk.funnycombination.ui.highscores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.domain.usecase.GetTopScoresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана рекордов.
 * Отвечает за загрузку и отображение списка лучших результатов.
 */
@HiltViewModel
class HighScoresViewModel @Inject constructor(
    private val getTopScoresUseCase: GetTopScoresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HighScoresState())
    val state = _state.asStateFlow()

    init {
        loadHighScores()
    }

    private fun loadHighScores() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getTopScoresUseCase()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            scores = result.data,
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            error = "Failed to load scores",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}