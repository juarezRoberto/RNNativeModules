package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerSaved
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTDummySurveyApplyAnswersRepository : ZEMTSurveyApplyAnswersRepository {

    override suspend fun saveAnswer(answer: ZEMTSurveyApplyAnswerSaved) { }

    override suspend fun getAnswersSaved(): List<ZEMTSurveyApplyAnswerSaved> {
        return emptyList()
    }

    override val totalAnswersSaved: Flow<Int>
        get() = flow { emit(0) }

    override suspend fun setTotalAnswersToSave(totalAnswers: Int) { }

    override suspend fun getTotalAnswersToSave(): Int = 0

    override suspend fun deleteAll() {

    }
}