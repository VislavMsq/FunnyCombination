package com.mosiuk.funnycombination.domain.usecase

import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.domain.repository.HighScoreRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Use-case для вставки нового рекорда, если он является лучшим.
 * Содержит бизнес-логику проверки и форматирования даты.
 */
class InsertHighScoreIfBestUseCase @Inject constructor(
    private val repository: HighScoreRepository
) {
    suspend operator fun invoke(score: Int): Result<Unit> {
        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        return repository.insertIfBest(score, currentDate)
    }
}