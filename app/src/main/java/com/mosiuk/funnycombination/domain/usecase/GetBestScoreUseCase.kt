package com.mosiuk.funnycombination.domain.usecase

import com.mosiuk.funnycombination.core.Result
import com.mosiuk.funnycombination.domain.repository.HighScoreRepository
import javax.inject.Inject

/**
 * Use-case (сценарий использования) для получения лучшего счета.
 * Инкапсулирует одну конкретную бизнес-операцию.
 * Зависит от абстракции (интерфейса) репозитория, а не от реализации.
 */
class GetBestScoreUseCase @Inject constructor(
    private val repository: HighScoreRepository
) {
    suspend operator fun invoke(): Result<Int> {
        return when (val result = repository.getBestScore()) {
            is Result.Success -> Result.Success(result.data ?: 0) // Возвращаем 0, если рекордов нет
            is Result.Error -> result
        }
    }
}