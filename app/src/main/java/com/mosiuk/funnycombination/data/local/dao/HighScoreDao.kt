package com.mosiuk.funnycombination.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mosiuk.funnycombination.data.local.entity.HighScoreEntity

/**
 * DAO (Data Access Object) для работы с таблицей рекордов.
 * Определяет SQL-запросы для взаимодействия с базой данных Room.
 */
@Dao
interface HighScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(highScore: HighScoreEntity)

    @Query("SELECT * FROM high_scores ORDER BY score DESC LIMIT 10")
    suspend fun getTopScores(): List<HighScoreEntity>

    @Query("SELECT MAX(score) FROM high_scores")
    suspend fun getBestScore(): Int?
}