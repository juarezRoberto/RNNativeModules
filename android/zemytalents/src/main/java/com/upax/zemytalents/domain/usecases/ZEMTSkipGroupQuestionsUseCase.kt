package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTSaveAnswerError
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.repositories.ZEMTGroupQuestionsDiscoverRepository
import kotlinx.coroutines.flow.first

internal class ZEMTSkipGroupQuestionsUseCase(
    private val updateGroupQuestionIndexUseCase: ZEMTUpdateGroupQuestionsIndexDiscoverUseCase,
    private val saveAnswerUseCase: ZEMTSaveAnswerDiscoverUserCase,
    private val groupQuestionsRepository: ZEMTGroupQuestionsDiscoverRepository
) {

    suspend operator fun invoke(answerId: ZEMTAnswerId): ZEMTResult<Unit, ZEMTSaveAnswerError> {
        return if (isLastQuestion()) {
            saveAnswerUseCase.invoke(answerId)
        } else {
            updateGroupQuestionIndexUseCase.invoke()
            ZEMTResult.Success(Unit)
        }
    }

    private suspend fun isLastQuestion(): Boolean {
        val answersPendingToResponse = groupQuestionsRepository.getTotalGroupQuestions() -
                groupQuestionsRepository.totalGroupQuestionsAnswered.first()
        return answersPendingToResponse <= 1
    }

}