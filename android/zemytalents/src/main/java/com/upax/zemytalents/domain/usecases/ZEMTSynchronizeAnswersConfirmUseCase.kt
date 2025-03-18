package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.local.database.dao.ZEMTModuleDao
import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.flatMap
import com.upax.zemytalents.domain.models.modules.ZEMTAnswer
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.domain.repositories.ZEMTAnswersSynchronizer
import com.upax.zemytalents.domain.repositories.ZEMTSurveyConfirmRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository

internal class ZEMTSynchronizeAnswersConfirmUseCase(
    private val answerSynchronizer: ZEMTAnswersSynchronizer,
    private val userRepository: ZEMTUserRepository,
    private val surveyConfirmRepository: ZEMTSurveyConfirmRepository,
    private val moduleDao: ZEMTModuleDao
) {

    suspend operator fun invoke(
    ): ZEMTResult<Unit, ZEMTDataError> {

        val answers = mutableListOf<ZEMTAnswer>()

        surveyConfirmRepository.getSavedAnswers().forEach { savedAnswer ->
            val answer = object : ZEMTAnswer(
                id = ZEMTAnswerId(savedAnswer.answerId),
                questionId = ZEMTQuestionId(savedAnswer.questionId),
                date = savedAnswer.date,
                location = ZEMTLocation(savedAnswer.latitude, savedAnswer.longitude)
            ) {
                override val position: Int
                    get() = savedAnswer.id
            }
            answers.add(answer)
        }

        return answerSynchronizer.sync(
            collaboratorId = userRepository.collaboratorId,
            surveyId = surveyConfirmRepository.getId(),
            answers = answers
        ).flatMap {
            moduleDao.updateCompleteStatus(surveyConfirmRepository.getId().value.toInt(), true)
            ZEMTResult.Success(Unit)
        }
    }

}