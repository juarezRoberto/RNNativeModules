package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerSaved
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTFakeSurveyApplyAnswersRepository(
    private val _totalAnswersSaved: Int = ZEMTRandomValuesUtil.getInt()
): ZEMTSurveyApplyAnswersRepository {

    override val totalAnswersSaved: Flow<Int>
        get() = flow { emit(_totalAnswersSaved) }

    private var totalAnswersToSave = 0

    private val answersSaved = mutableListOf<ZEMTSurveyApplyAnswerSaved>()

    override suspend fun saveAnswer(answer: ZEMTSurveyApplyAnswerSaved) {
        answersSaved.add(answer)
    }

    override suspend fun getAnswersSaved(): List<ZEMTSurveyApplyAnswerSaved> {
        return answersSaved
    }

    override suspend fun setTotalAnswersToSave(totalAnswers: Int) {
        this.totalAnswersToSave = totalAnswers
    }

    override suspend fun getTotalAnswersToSave(): Int {
        return totalAnswersToSave
    }

    override suspend fun deleteAll() {
        answersSaved.clear()
    }
}