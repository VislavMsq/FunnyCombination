package com.mosiuk.funnycombination.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HighScore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val score: Int
)