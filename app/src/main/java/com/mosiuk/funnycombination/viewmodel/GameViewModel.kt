package com.mosiuk.funnycombination.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel(
    private val emojiPool: List<String> = listOf("🍎", "🍌", "🍇", "🍒", "🍊")
) : ViewModel() {
    var sequence by mutableStateOf(listOf(emojiPool.random()))
        private set
    var playerInput by mutableStateOf(listOf<String>())
        private set
    var isShowingSequence by mutableStateOf(false)
        private set
    var readyForNextRound by mutableStateOf(false)
        private set
    var currentMessage by mutableStateOf<String?>(null)
        private set
    var showCorrectRow by mutableStateOf(false)
        private set
    var showMistake by mutableStateOf(false)
        private set
    var mistakeInput by mutableStateOf<List<String>>(emptyList())
        private set

    val level: Int get() = sequence.size


    fun startGame() {
        sequence = listOf(emojiPool.random())
        playerInput = listOf()
        isShowingSequence = false
        readyForNextRound = false
        currentMessage = "Начало игры!"
        showCorrectRow = false
        showMistake = false
        mistakeInput = emptyList()
        viewModelScope.launch {
            delay(1200)
            isShowingSequence = true
            currentMessage = null
        }
    }


    fun onSequenceShown() {
        isShowingSequence = false
        playerInput = listOf()
        currentMessage = null
        showCorrectRow = false
        showMistake = false
        mistakeInput = emptyList()
    }

    fun onEmojiClick(emoji: String, onGameOver: (score: Int) -> Unit) {
        if (isShowingSequence || readyForNextRound || currentMessage != null || showCorrectRow || showMistake) return

        val newPlayerInput = playerInput + emoji
        playerInput = newPlayerInput

        val mistake =
            newPlayerInput.zip(sequence.take(newPlayerInput.size)).any { (a, b) -> a != b }
        if (mistake) {
            mistakeInput = newPlayerInput
            showMistake = true
            currentMessage = "Ошибка! Сравни:"
            viewModelScope.launch {
                delay(1800)
                showMistake = false
                currentMessage = null
                onGameOver(sequence.size - 1)
            }
        } else if (newPlayerInput.size == sequence.size) {
            showCorrectRow = true
            currentMessage = "Успех! Сравни свой ответ:"
            viewModelScope.launch {
                delay(1800)
                readyForNextRound = true
            }
        }
    }

    fun nextLevel() {
        sequence = sequence + emojiPool.random()
        playerInput = listOf()
        isShowingSequence = false
        readyForNextRound = false
        currentMessage = "Начало раунда!"
        showCorrectRow = false
        showMistake = false
        mistakeInput = emptyList()
        viewModelScope.launch {
            delay(1200)
            isShowingSequence = true
            currentMessage = null
        }
    }
}
