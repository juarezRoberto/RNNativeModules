package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.data.local.database.entity.confirm.ZEMTSurveyConfirmAnswerSavedEntity
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestion
import kotlinx.coroutines.flow.Flow

internal interface ZEMTSurveyConfirmRepository {

    suspend fun getId(): ZEMTSurveyId

    fun saveTotalQuestions(surveyTalents: List<ZEMTSurveyTalent>)

    fun getTotalQuestions(): Int

    suspend fun getSavedAnswers(): List<ZEMTSurveyConfirmAnswerSavedEntity>

    suspend fun hasSavedAnswers(): Boolean

    suspend fun saveConfirmSurveyAnswer(entity: ZEMTSurveyConfirmAnswerSavedEntity)

    suspend fun getLatestSavedAnswer(): ZEMTSurveyConfirmAnswerSavedEntity

    suspend fun deleteAnswersByTalentId(id: Int)

    suspend fun deleteAllSavedAnswers()

    suspend fun getNextQuestion(talents: List<ZEMTSurveyTalent>): Pair<Int, ZEMTSurveyTalentQuestion>

    fun collectTotalQuestionsAnswered(): Flow<Float>
}