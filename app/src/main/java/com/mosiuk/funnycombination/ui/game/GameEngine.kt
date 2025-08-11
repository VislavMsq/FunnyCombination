package com.mosiuk.funnycombination.ui.game

import javax.inject.Inject
import kotlin.random.Random

/**
 * Чистый, не зависящий от Android, движок игровой логики.
 * Отвечает за создание последовательностей и проверку ввода пользователя.
 * Легко тестируется, так как не имеет внешних зависимостей.
 */
class GameEngine @Inject constructor() {

    private val emojiPool = listOf("😂", "😍", "😭", "🤔", "😎", "😡", "🤯", "😱", "😇", "🥳")

    /**
     * Создает новую последовательность эмодзи для указанного уровня.
     * Длина последовательности зависит от уровня.
     */
    fun createNewSequence(level: Int): List<String> {
        val sequenceLength = level + INITIAL_SEQUENCE_LENGTH - 1
        return List(sequenceLength) { emojiPool[Random.nextInt(emojiPool.size)] }
    }

    /**
     * Проверяет ввод пользователя на соответствие правильной последовательности.
     */
    fun validateInput(userInput: List<String>, correctSequence: List<String>): Boolean {
        return userInput == correctSequence
    }

    companion object {
        private const val INITIAL_SEQUENCE_LENGTH = 3
    }
}