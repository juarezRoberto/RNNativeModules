package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerSavedDiscoverEntity
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.repositories.ZEMTDateRepository
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository

internal class ZEMTAnswerOptionDiscoverToEntityMapper(
    private val dateRepository: ZEMTDateRepository,
    private val locationRepository: ZEMTLocationRepository,
) : Function2<ZEMTAnswerOptionDiscover, Int, ZEMTAnswerSavedDiscoverEntity> {

    override fun invoke(
        answer: ZEMTAnswerOptionDiscover,
        groupQuestionIndex: Int
    ): ZEMTAnswerSavedDiscoverEntity {
        val location = locationRepository.getLocation()
        return ZEMTAnswerSavedDiscoverEntity(
            questionId = answer.questionId.value,
            answerOptionId = answer.id.value,
            date = dateRepository.currentDate(),
            text = answer.text,
            latitude = location.latitude,
            longitude = location.longitude,
            value = answer.value,
            groupQuestionIndex = groupQuestionIndex
        )
    }
}