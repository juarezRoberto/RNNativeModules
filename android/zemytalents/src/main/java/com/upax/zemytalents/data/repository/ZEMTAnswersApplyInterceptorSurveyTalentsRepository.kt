package com.upax.zemytalents.data.repository

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.flatMap
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyTalentsRepository

internal class ZEMTAnswersApplyInterceptorSurveyTalentsRepository(
    private val surveyRepository: ZEMTSurveyTalentsRepository,
    private val answersRepository: ZEMTSurveyApplyAnswersRepository
) : ZEMTSurveyTalentsRepository {

    override suspend fun get(surveyId: ZEMTSurveyId): ZEMTResult<List<ZEMTSurveyTalent>, ZEMTDataError> {
        return surveyRepository.get(surveyId).flatMap { surveyTalents ->
            var totalAnswers = 0
            surveyTalents.forEach { talent ->
                talent.questions.forEach { question ->
                    totalAnswers += question.answerOptions.count()
                }
            }
            answersRepository.setTotalAnswersToSave(totalAnswers)
            ZEMTResult.Success(surveyTalents)
        }
    }
}