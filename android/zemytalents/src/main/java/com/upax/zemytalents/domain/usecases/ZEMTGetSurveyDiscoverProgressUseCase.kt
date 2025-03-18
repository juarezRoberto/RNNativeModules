package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.repositories.ZEMTGroupQuestionsDiscoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ZEMTGetSurveyDiscoverProgressUseCase(
    private val groupQuestionsRepository: ZEMTGroupQuestionsDiscoverRepository
) {
    operator fun invoke(): Flow<Float> = groupQuestionsRepository.totalGroupQuestionsAnswered.map {
        val defaultProgress = 0.01f
        val progress = try {
            it.toFloat() / groupQuestionsRepository.getTotalGroupQuestions().toFloat()
        } catch (_: Exception) {
            defaultProgress
        }
        if (progress <= 0 || progress.isInfinite() || progress.isNaN())
            defaultProgress
        else
            progress
    }
}
