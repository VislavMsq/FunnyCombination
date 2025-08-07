package com.mosiuk.funnycombination.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosiuk.funnycombination.data.HighScore
import com.mosiuk.funnycombination.data.HighScoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HighScoreViewModel(
    private val repository: HighScoreRepository
) : ViewModel() {

    private val _scores = MutableStateFlow<List<HighScore>>(emptyList())
    val scores: StateFlow<List<HighScore>> = _scores

    fun loadScores() {
        viewModelScope.launch {
            _scores.value = repository.getAll()
        }
    }
    suspend fun isNewRecord(score: Int): Boolean {
        val best = repository.getBestScore() ?: 0
        return score >= best && score > 0
    }

    fun saveHighScore(score: Int) {
        viewModelScope.launch {
            val date = java.text.SimpleDateFormat("yyyy-MM-dd").format(java.util.Date())
            repository.insertIfBest(score, date)
        }
    }
}
