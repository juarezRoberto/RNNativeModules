package com.upax.zemytalents.data.remote.requests

import com.upax.zemytalents.domain.models.modules.ZEMTAnswer
import com.upax.zemytalents.domain.repositories.ZEMTDeviceRepository

internal class ZEMTSyncSurveyAnswersRequestBuilder(
    private val deviceRepository: ZEMTDeviceRepository
) {

    fun build(
        collaboratorId: String,
        answers: List<ZEMTAnswer>
    ): ZEMTSyncSurveyDiscoverAnswersRequest {

        val answersOrdered = answers.sortedBy { it.position }
        val firstAnswer = answersOrdered.first()
        val lastAnswer = answersOrdered.last()

        return ZEMTSyncSurveyDiscoverAnswersRequest(
            collaboratorId = collaboratorId,
            initialLatitude = firstAnswer.location.latitude,
            initialLongitude = firstAnswer.location.longitude,
            endLatitude = lastAnswer.location.latitude,
            endLongitude = lastAnswer.location.longitude,
            initialDate = firstAnswer.date,
            endDate = lastAnswer.date,
            device = deviceRepository.name,
            platform = deviceRepository.platform,
            answers = answers.map {
                ZEMTAnswersSurveyDiscoverRequest(
                    idQuestion = it.questionId.value,
                    idAnswer = it.id.value,
                    latitude = it.location.latitude,
                    longitude = it.location.longitude,
                    date = it.date
                )
            }
        )
    }

}