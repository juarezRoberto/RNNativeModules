package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerSaved
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus
import kotlinx.coroutines.flow.Flow

internal interface ZEMTSurveyApplyAnswersRepository {

    val totalAnswersSaved: Flow<Int>

    suspend fun saveAnswer(answer: ZEMTSurveyApplyAnswerSaved)

    suspend fun getAnswersSaved(): List<ZEMTSurveyApplyAnswerSaved>

    suspend fun setTotalAnswersToSave(totalAnswers: Int)

    suspend fun getTotalAnswersToSave(): Int

    suspend fun deleteAll()

}