package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTSaveAnswerError
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.repositories.ZEMTAnswerOptionDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTAnswerSavedDiscoverRepository

internal class ZEMTSaveAnswerDiscoverUserCase(
    private val answerOptionRepository: ZEMTAnswerOptionDiscoverRepository,
    private val answerSavedRepository: ZEMTAnswerSavedDiscoverRepository,
    private val updateGroupQuestionIndexUseCase: ZEMTUpdateGroupQuestionsIndexDiscoverUseCase
) {

    suspend operator fun invoke(answerId: ZEMTAnswerId): ZEMTResult<Unit, ZEMTSaveAnswerError> {
        val answer = answerOptionRepository.getAnswerById(answerId)

        if (!couldAnswerQuestion(answer)) {
            return ZEMTResult.Error(ZEMTSaveAnswerError.MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED)
        }

        answerSavedRepository.saveAnswer(answer)
        updateGroupQuestionIndexUseCase.invoke()
        return ZEMTResult.Success(Unit)
    }

    private suspend fun couldAnswerQuestion(answer: ZEMTAnswerOptionDiscover): Boolean {
        if (!answer.isNeutralAnswer) return true
        return answerSavedRepository.getTotalNeutralAnswers() < MAX_NUMBER_OF_NEUTRAL_ANSWERS
    }

    companion object {
        private const val MAX_NUMBER_OF_NEUTRAL_ANSWERS = 30
    }

}