package com.mosiuk.funnycombination.data.repository

import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.data.local.dao.HighScoreDao
import com.mosiuk.funnycombination.data.local.entity.HighScoreEntity
import com.mosiuk.funnycombination.data.mapper.HighScoreEntityToDomainMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HighScoreRepositoryImplTest {

    private lateinit var highScoreDao: HighScoreDao
    private lateinit var mapper: HighScoreEntityToDomainMapper
    private lateinit var repository: HighScoreRepositoryImpl

    @Before
    fun setUp() {
        highScoreDao = mockk(relaxed = true)
        mapper = HighScoreEntityToDomainMapper() // Using real mapper
        repository = HighScoreRepositoryImpl(highScoreDao, mapper)
    }

    @Test
    fun `insertIfBest inserts score when it is higher than best score`() = runTest {
        // Given
        val newScore = 10
        val bestScore = 5
        coEvery { highScoreDao.getBestScore() } returns bestScore

        // When
        val result = repository.insertIfBest(newScore, "11.08.2025")

        // Then
        coVerify(exactly = 1) { highScoreDao.insertScore(any()) }
        assertTrue(result is Result.Success)
    }

    @Test
    fun `insertIfBest does not insert score when it is not higher`() = runTest {
        // Given
        val newScore = 3
        val bestScore = 5
        coEvery { highScoreDao.getBestScore() } returns bestScore

        // When
        val result = repository.insertIfBest(newScore, "11.08.2025")

        // Then
        coVerify(exactly = 0) { highScoreDao.insertScore(any()) }
        assertTrue(result is Result.Success)
    }

    @Test
    fun `getAll returns list of high scores successfully`() = runTest {
        // Given
        val entities = listOf(HighScoreEntity(1, "10.08.2025", 10))
        coEvery { highScoreDao.getTopScores() } returns entities

        // When
        val result = repository.getAll()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(1, data.size)
        assertEquals(10, data[0].score)
    }

    @Test
    fun `getAll returns error when dao throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Database error")
        coEvery { highScoreDao.getTopScores() } throws exception

        // When
        val result = repository.getAll()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }
}
