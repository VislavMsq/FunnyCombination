package com.mosiuk.funnycombination.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HighScoreDao {

    @Insert
    suspend fun insert(highScore: HighScore)

    @Query("SELECT * FROM HighScore ORDER BY score DESC LIMIT 10")
    suspend fun getAll(): List<HighScore>

    @Query("SELECT MAX(score) FROM HighScore")
    suspend fun getBestScore(): Int?
}