package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.flatMap
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestion
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestionOption
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerSaved
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyTalentsRepository

internal class ZEMTGetSurveyApplyUseCase(
    private val talentsRepository: ZEMTSurveyTalentsRepository,
    private val surveyApplyRepository: ZEMTSurveyApplyRepository,
    private val surveyApplyAnswersRepository: ZEMTSurveyApplyAnswersRepository
) {

    suspend operator fun invoke(): ZEMTResult<List<ZEMTSurveyTalent>, ZEMTDataError> {
        return talentsRepository.get(surveyApplyRepository.getId()).flatMap { surveyTalents ->
            return ZEMTResult.Success(addAnswersSavedToSurvey(surveyTalents))
        }
    }

    private suspend fun addAnswersSavedToSurvey(
        surveyTalents: List<ZEMTSurveyTalent>
    ): List<ZEMTSurveyTalent> {
        val answersSaved = surveyApplyAnswersRepository.getAnswersSaved()
        return surveyTalents.map { it.copy(questions = mergeQuestions(it.questions, answersSaved)) }
    }

    private fun mergeQuestions(
        questions: List<ZEMTSurveyTalentQuestion>,
        answersSaved: List<ZEMTSurveyApplyAnswerSaved>
    ): List<ZEMTSurveyTalentQuestion> {
        return questions.map { it.copy(answerOptions = mergeAnswers(it.answerOptions, answersSaved)) }
    }

    private fun mergeAnswers(
        answerOptions: List<ZEMTSurveyTalentQuestionOption>,
        answersSaved: List<ZEMTSurveyApplyAnswerSaved>
    ): List<ZEMTSurveyTalentQuestionOption> {
        return answerOptions.map { answerOption ->
            val answerOptionSaved = answersSaved.find {
                it.id == answerOption.id
            } ?: return@map answerOption
            answerOption.copy(status = answerOptionSaved.status)
        }
    }

}