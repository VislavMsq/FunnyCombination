package com.mosiuk.funnycombination.ui.game

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameEngineTest {

    private lateinit var gameEngine: GameEngine

    @Before
    fun setUp() {
        gameEngine = GameEngine()
    }

    @Test
    fun `createNewSequence generates sequence with correct length for level 1`() {
        val level = 1
        val sequence = gameEngine.createNewSequence(level)
        // Length = level + INITIAL_SEQUENCE_LENGTH - 1 = 1 + 3 - 1 = 3
        assertEquals(3, sequence.size)
    }

    @Test
    fun `createNewSequence generates sequence with correct length for level 5`() {
        val level = 5
        val sequence = gameEngine.createNewSequence(level)
        // Length = level + INITIAL_SEQUENCE_LENGTH - 1 = 5 + 3 - 1 = 7
        assertEquals(7, sequence.size)
    }

    @Test
    fun `validateInput returns true for correct sequence`() {
        val correctSequence = listOf("😎", "😍", "😇")
        val userInput = listOf("😎", "😍", "😇")
        val result = gameEngine.validateInput(userInput, correctSequence)
        assertTrue(result)
    }

    @Test
    fun `validateInput returns false for incorrect sequence`() {
        val correctSequence = listOf("😎", "😍", "😇")
        val userInput = listOf("😎", "😭", "😇")
        val result = gameEngine.validateInput(userInput, correctSequence)
        assertFalse(result)
    }

    @Test
    fun `validateInput returns false for sequence with wrong length`() {
        val correctSequence = listOf("😎", "😍", "😇")
        val userInput = listOf("😎", "😍")
        val result = gameEngine.validateInput(userInput, correctSequence)
        assertFalse(result)
    }
}
