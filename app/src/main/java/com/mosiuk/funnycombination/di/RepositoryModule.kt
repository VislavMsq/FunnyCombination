package com.mosiuk.funnycombination.di

import com.mosiuk.funnycombination.data.repository.HighScoreRepositoryImpl
import com.mosiuk.funnycombination.domain.repository.HighScoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt-модуль для предоставления реализаций репозиториев.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Связывает (binds) интерфейс [HighScoreRepository] с его реализацией [HighScoreRepositoryImpl].
     * Когда кто-то запрашивает [HighScoreRepository], Hilt предоставит [HighScoreRepositoryImpl].
     */
    @Binds
    @Singleton
    abstract fun bindHighScoreRepository(
        highScoreRepositoryImpl: HighScoreRepositoryImpl
    ): HighScoreRepository
}