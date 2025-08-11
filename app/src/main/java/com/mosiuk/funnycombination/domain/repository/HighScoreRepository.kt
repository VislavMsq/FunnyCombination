package com.mosiuk.funnycombination.domain.repository

import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.domain.model.HighScore

/**
 * Интерфейс (контракт) для репозитория рекордов.
 * Определяет, какие операции с данными рекордов доступны для доменного слоя.
 * Доменный слой зависит от этого интерфейса, а не от его конкретной реализации.
 */
interface HighScoreRepository {
    /**
     * Вставляет новый счет, если он является лучшим.
     * @param score Новый счет.
     * @param date Дата установки рекорда.
     */
    suspend fun insertIfBest(score: Int, date: String): Result<Unit>

    /**
     * Получает список всех рекордов (обычно топ-N).
     */
    suspend fun getAll(): Result<List<HighScore>>

    /**
     * Получает лучший результат.
     */
    suspend fun getBestScore(): Result<Int?>
}