package com.mosiuk.funnycombination.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity-класс для Room. Представляет таблицу `high_scores` в базе данных.
 * Это деталь реализации слоя Data, и другие слои не должны знать об этом классе.
 */
@Entity(tableName = "high_scores")
data class HighScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val score: Int
)