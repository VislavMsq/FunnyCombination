package com.mosiuk.funnycombination.data.repository

import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.data.local.dao.HighScoreDao
import com.mosiuk.funnycombination.data.local.entity.HighScoreEntity
import com.mosiuk.funnycombination.data.mapper.HighScoreEntityToDomainMapper
import com.mosiuk.funnycombination.domain.model.HighScore
import com.mosiuk.funnycombination.domain.repository.HighScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Реализация интерфейса [HighScoreRepository].
 * Слой Domain зависит от интерфейса, а не от этой реализации.
 * Этот класс инкапсулирует логику работы с источником данных (DAO) и маппинг моделей.
 */
class HighScoreRepositoryImpl @Inject constructor(
    private val highScoreDao: HighScoreDao,
    private val mapper: HighScoreEntityToDomainMapper
) : HighScoreRepository {

    override suspend fun insertIfBest(score: Int, date: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val bestScore = highScoreDao.getBestScore() ?: 0
            if (score > bestScore) {
                highScoreDao.insertScore(HighScoreEntity(date = date, score = score))
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAll(): Result<List<HighScore>> = withContext(Dispatchers.IO) {
        try {
            val scores = highScoreDao.getTopScores().map { mapper.map(it) }
            Result.Success(scores)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getBestScore(): Result<Int?> = withContext(Dispatchers.IO) {
        try {
            Result.Success(highScoreDao.getBestScore())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}