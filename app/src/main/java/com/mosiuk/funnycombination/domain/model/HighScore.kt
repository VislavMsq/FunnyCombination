package com.mosiuk.funnycombination.domain.model

/**
 * Чистая модель данных для доменного слоя.
 * Представляет собой запись о рекорде в терминах бизнес-логики,
 * не зависит от способа хранения (база данных) или отображения (UI).
 */
data class HighScore(
    val id: Int = 0,
    val date: String,
    val score: Int
)