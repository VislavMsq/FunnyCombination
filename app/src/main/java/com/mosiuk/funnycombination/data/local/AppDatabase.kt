package com.mosiuk.funnycombination.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mosiuk.funnycombination.data.local.dao.HighScoreDao
import com.mosiuk.funnycombination.data.local.entity.HighScoreEntity

/**
 * Основной класс базы данных Room.
 * Определяет список Entity-классов и версию БД.
 */
@Database(entities = [HighScoreEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun highScoreDao(): HighScoreDao

    companion object {
        const val DATABASE_NAME = "funny_combination_db"
    }
}