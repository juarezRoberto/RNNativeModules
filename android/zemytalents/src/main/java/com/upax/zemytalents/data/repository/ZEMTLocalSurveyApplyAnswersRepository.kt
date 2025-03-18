package com.upax.zemytalents.data.repository

import com.prometeo.keymanager.preferences.ZKMPreferences
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyApplyDao
import com.upax.zemytalents.data.local.database.entity.apply.ZEMTSurveyApplyAnswerSavedEntity
import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerSaved
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import kotlinx.coroutines.flow.Flow

internal class ZEMTLocalSurveyApplyAnswersRepository(
    private val dao: ZEMTSurveyApplyDao,
    private val preferences: ZKMPreferences
) : ZEMTSurveyApplyAnswersRepository {

    override val totalAnswersSaved: Flow<Int>
        get() = dao.getTotalAnswers()

    override suspend fun saveAnswer(
        answer: ZEMTSurveyApplyAnswerSaved
    ) = dao.insertAnswer(
        ZEMTSurveyApplyAnswerSavedEntity(
            answerId = answer.id,
            status = answer.status,
            questionId = answer.questionId,
            longitude = answer.location.longitude,
            latitude = answer.location.latitude,
            date = answer.date
        )
    )

    override suspend fun getAnswersSaved(): List<ZEMTSurveyApplyAnswerSaved> {
        return dao.getAllAnswers().map { answer ->
            ZEMTSurveyApplyAnswerSaved(
                id = answer.answerId,
                status = answer.status,
                questionId = answer.questionId,
                location = ZEMTLocation(latitude = answer.latitude, longitude = answer.longitude),
                date = answer.date,
                order = answer.id
            )
        }
    }

    override suspend fun getTotalAnswersToSave(): Int {
        return preferences.getInt(KEY_TOTAL_ANSWERS_TO_SAVE, 0)
    }

    override suspend fun setTotalAnswersToSave(totalAnswers: Int) {
        preferences.put(KEY_TOTAL_ANSWERS_TO_SAVE, totalAnswers)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    companion object {
        private const val KEY_TOTAL_ANSWERS_TO_SAVE = "KEY_TOTAL_ANSWERS_TO_SAVE"
    }

}