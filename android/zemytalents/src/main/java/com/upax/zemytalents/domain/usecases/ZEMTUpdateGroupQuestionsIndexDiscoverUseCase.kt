package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.repositories.ZEMTGroupQuestionsDiscoverRepository

internal class ZEMTUpdateGroupQuestionsIndexDiscoverUseCase(
    private val groupQuestionsRepository: ZEMTGroupQuestionsDiscoverRepository
) {

    suspend operator fun invoke() {
        val nexQuestionIndex = groupQuestionsRepository.getNextGroupQuestionIndex()
        if (nexQuestionIndex != null) {
            groupQuestionsRepository.updateGroupQuestionIndex(index = nexQuestionIndex)
        }
    }

}