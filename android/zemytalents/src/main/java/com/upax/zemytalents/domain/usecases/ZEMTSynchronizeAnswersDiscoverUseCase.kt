package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.flatMap
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTAnswerSavedDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTAnswersSynchronizer
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository

internal class ZEMTSynchronizeAnswersDiscoverUseCase(
    private val answerSavedRepository: ZEMTAnswerSavedDiscoverRepository,
    private val answerSynchronizer: ZEMTAnswersSynchronizer,
    private val userRepository: ZEMTUserRepository,
    private val surveyDiscoverRepository: ZEMTSurveyDiscoverRepository
) {

    suspend operator fun invoke(): ZEMTResult<Unit, ZEMTDataError> {
        val answers = answerSavedRepository.getAnswers().filter { !it.isNeutralAnswer }
        return answerSynchronizer.sync(
            collaboratorId = userRepository.collaboratorId,
            surveyId = surveyDiscoverRepository.getId(),
            answers = answers
        ).flatMap {
            answerSavedRepository.deleteAll()
            return ZEMTResult.Success(it)
        }
    }

}