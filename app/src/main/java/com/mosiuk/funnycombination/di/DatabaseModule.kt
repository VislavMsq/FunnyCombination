package com.mosiuk.funnycombination.di

import android.content.Context
import androidx.room.Room
import com.mosiuk.funnycombination.data.local.AppDatabase
import com.mosiuk.funnycombination.data.local.dao.HighScoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt-модуль для предоставления зависимостей, связанных с базой данных.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Предоставляет экземпляр базы данных [AppDatabase] как Singleton.
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    /**
     * Предоставляет экземпляр [HighScoreDao].
     */
    @Provides
    fun provideHighScoreDao(database: AppDatabase): HighScoreDao {
        return database.highScoreDao()
    }
}