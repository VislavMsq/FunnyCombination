package com.mosiuk.funnycombination.domain.usecase

import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.domain.model.HighScore
import com.mosiuk.funnycombination.domain.repository.HighScoreRepository
import javax.inject.Inject

/**
 * Use-case для получения списка лучших результатов.
 */
class GetTopScoresUseCase @Inject constructor(
    private val repository: HighScoreRepository
) {
    suspend operator fun invoke(): Result<List<HighScore>> {
        return repository.getAll()
    }
}