package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTAnswer
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.domain.repositories.ZEMTAnswersSynchronizer
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyRepository
import com.upax.zemytalents.domain.repositories.ZEMTTalentsCompletedRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository

internal class ZEMTSynchronizeApplyAnswersUseCase(
    private val userRepository: ZEMTUserRepository,
    private val surveyApplyRepository: ZEMTSurveyApplyRepository,
    private val answerSynchronizer: ZEMTAnswersSynchronizer,
    private val surveyApplyAnswersRepository: ZEMTSurveyApplyAnswersRepository,
    private val talentsCompletedRepository: ZEMTTalentsCompletedRepository
) {

    suspend operator fun invoke(): ZEMTResult<Unit, ZEMTDataError> {
        val answersSaved = surveyApplyAnswersRepository.getAnswersSaved().filter {
            it.status == ZEMTSurveyApplyAnswerStatus.CHECK_POSITIVE
        }

        val answersToSync = answersSaved.map { answer ->
            object : ZEMTAnswer(
                id = ZEMTAnswerId(answer.id),
                questionId = ZEMTQuestionId(answer.questionId),
                date = answer.date,
                location = answer.location
            ) {
                override val position: Int get() = answer.order
            }
        }

        val result = answerSynchronizer.sync(
            collaboratorId = userRepository.collaboratorId,
            surveyId = surveyApplyRepository.getId(),
            answers = answersToSync
        )

        return when (result) {
            is ZEMTResult.Error -> result
            is ZEMTResult.Success -> {
                surveyApplyRepository.finish()
                surveyApplyAnswersRepository.deleteAll()
                talentsCompletedRepository.completeTalents(userRepository.collaboratorId)
                ZEMTResult.Success(Unit)
            }
        }
    }

}