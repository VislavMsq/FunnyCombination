package com.mosiuk.funnycombination.data.mapper

import com.mosiuk.funnycombination.core.Mapper
import com.mosiuk.funnycombination.data.local.entity.HighScoreEntity
import com.mosiuk.funnycombination.domain.model.HighScore
import javax.inject.Inject

/**
 * Маппер для преобразования [HighScoreEntity] (модель слоя Data) в [HighScore] (модель слоя Domain).
 */
class HighScoreEntityToDomainMapper @Inject constructor() : Mapper<HighScoreEntity, HighScore> {
    override fun map(input: HighScoreEntity): HighScore {
        return HighScore(
            id = input.id,
            date = input.date,
            score = input.score
        )
    }
}

/**
 * Маппер для преобразования [HighScore] (модель слоя Domain) в [HighScoreEntity] (модель слоя Data).
 * В данном проекте не используется, но является хорошей практикой для полноты.
 */
class HighScoreDomainToEntityMapper @Inject constructor() : Mapper<HighScore, HighScoreEntity> {
    override fun map(input: HighScore): HighScoreEntity {
        return HighScoreEntity(
            id = input.id,
            date = input.date,
            score = input.score
        )
    }
}