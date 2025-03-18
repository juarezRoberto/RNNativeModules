package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTModuleDao
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyConfirmAnswerSavedDao
import com.upax.zemytalents.data.local.database.entity.confirm.ZEMTSurveyConfirmAnswerSavedEntity
import com.upax.zemytalents.data.local.preferences.ZEMTLocalPreferences
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestion
import com.upax.zemytalents.domain.repositories.ZEMTSurveyConfirmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class ZEMTRoomSurveyConfirmRepository(
    private val modules: ZEMTModuleDao,
    private val answerSavedDao: ZEMTSurveyConfirmAnswerSavedDao,
    private val preferences: ZEMTLocalPreferences
) : ZEMTSurveyConfirmRepository {

    override suspend fun getId(): ZEMTSurveyId {
        return ZEMTSurveyId(
            value = modules.getModuleByStage(ZEMTModuleStage.CONFIRM.name)?.surveyId.orEmpty()
        )
    }

    override fun saveTotalQuestions(surveyTalents: List<ZEMTSurveyTalent>) {
        var totalQuestions = 0
        surveyTalents.forEach { talent ->
            totalQuestions += talent.questions.count()
        }
        preferences.confirmSurveyTotalQuestions = totalQuestions
    }

    override fun getTotalQuestions(): Int {
        return preferences.confirmSurveyTotalQuestions
    }

    override suspend fun getSavedAnswers(): List<ZEMTSurveyConfirmAnswerSavedEntity> {
        return withContext(Dispatchers.IO) { answerSavedDao.getSavedAnswers() }
    }

    override suspend fun hasSavedAnswers(): Boolean {
        return withContext(Dispatchers.IO) { answerSavedDao.hasSavedAnswers() }
    }

    override suspend fun saveConfirmSurveyAnswer(entity: ZEMTSurveyConfirmAnswerSavedEntity) {
        withContext(Dispatchers.IO) { answerSavedDao.insert(entity) }
    }

    override suspend fun getLatestSavedAnswer(): ZEMTSurveyConfirmAnswerSavedEntity {
        return withContext(Dispatchers.IO) { answerSavedDao.getLatestSavedAnswer() }
    }

    override suspend fun deleteAnswersByTalentId(id: Int) {
        withContext(Dispatchers.IO) { answerSavedDao.deleteAnswersByTalentId(id) }
    }

    override suspend fun deleteAllSavedAnswers() {
        withContext(Dispatchers.IO) { answerSavedDao.deleteAllSavedAnswers() }
    }

    override fun collectTotalQuestionsAnswered(): Flow<Float> {
        return answerSavedDao.collectSavedAnswers().map { answers ->
            val totalAnsweredQuestions = answers.groupBy { it.questionId }.size

            val defaultProgress = 0.01f
            val progress = try {
                totalAnsweredQuestions.toFloat() / preferences.confirmSurveyTotalQuestions.toFloat()
            } catch (_: Exception) {
                defaultProgress
            }
            if (progress <= 0 || progress.isInfinite() || progress.isNaN())
                defaultProgress
            else
                progress
        }.flowOn(Dispatchers.IO)
    }

    /** return next talentId, next question */
    override suspend fun getNextQuestion(
        talents: List<ZEMTSurveyTalent>
    ): Pair<Int, ZEMTSurveyTalentQuestion> {
        val latestSavedAnswer = getLatestSavedAnswer()
        //if saved answers is on latest question in talent, then move to next talent
        if (latestSavedAnswer.questionOrder == talents.first().questions.size) {
            // avoid crash on last talent and last question (reset that talent question answers)
            if (latestSavedAnswer.talentId == talents.last().id) {
                answerSavedDao.deleteAnswersByTalentId(latestSavedAnswer.talentId)
                return Pair(latestSavedAnswer.talentId, talents.last().questions.first())
            }
            // talentOrder start on 0
            val nextTalent = talents[latestSavedAnswer.talentOrder + 1]
            return Pair(nextTalent.id, nextTalent.questions.first())
        } else {
            talents.forEach { talent ->
                if (talent.id == latestSavedAnswer.talentId) {
                    // questionOrder start on 1
                    val nextQuestion = talent.questions[latestSavedAnswer.questionOrder]
                    return Pair(talent.id, nextQuestion)
                }
            }
        }
        return Pair(latestSavedAnswer.talentId, talents.first().questions.first())
    }

}