package com.mosiuk.funnycombination.data

class HighScoreRepository(private val dao: HighScoreDao) {
    suspend fun insertIfBest(score: Int, date: String) {
        val best = dao.getBestScore() ?: 0
        if (score > best) {
            dao.insert(HighScore(date = date, score = score))
        }
    }
    suspend fun getAll(): List<HighScore> = dao.getAll()
    suspend fun getBestScore(): Int? = dao.getBestScore()
}