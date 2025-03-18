package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ZEMTGetSurveyApplyProgressUseCase(
    private val answersRepository: ZEMTSurveyApplyAnswersRepository
) {

    operator fun invoke(): Flow<Float> = answersRepository.totalAnswersSaved.map {
        val defaultProgress = 0.01f
        val progress = try {
            it.toFloat() / answersRepository.getTotalAnswersToSave().toFloat()
        } catch (_: Exception) {
            defaultProgress
        }
        if (progress <= 0 || progress.isInfinite() || progress.isNaN())
            defaultProgress
        else
            progress
    }

}