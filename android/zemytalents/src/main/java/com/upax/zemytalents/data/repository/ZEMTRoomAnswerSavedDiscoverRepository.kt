package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTAnswerSavedDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTQuestionDiscoverDao
import com.upax.zemytalents.data.local.database.mapper.ZEMTAnswerOptionDiscoverToEntityMapper
import com.upax.zemytalents.data.local.database.mapper.ZEMTAnswerSavedDiscoverEntityToModelMapper
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerSavedDiscover
import com.upax.zemytalents.domain.repositories.ZEMTAnswerSavedDiscoverRepository

internal class ZEMTRoomAnswerSavedDiscoverRepository(
    private val answerSavedDao: ZEMTAnswerSavedDiscoverDao,
    private val questionDao: ZEMTQuestionDiscoverDao,
    private val answerOptionMapper: ZEMTAnswerOptionDiscoverToEntityMapper,
    private val answerSavedMapper: ZEMTAnswerSavedDiscoverEntityToModelMapper
) : ZEMTAnswerSavedDiscoverRepository {

    override suspend fun getTotalNeutralAnswers(): Int {
        return answerSavedDao.getTotalNeutralAnswers()
    }

    override suspend fun saveAnswer(answer: ZEMTAnswerOptionDiscover) {
        val question = questionDao.getQuestionById(answer.questionId.value)
        val answerSaved = answerOptionMapper.invoke(answer, question.groupQuestionIndex)
        answerSavedDao.insert(answerSaved)
    }

    override suspend fun getAnswers(): List<ZEMTAnswerSavedDiscover> {
        return answerSavedDao.getAll().map(answerSavedMapper)
    }

    override suspend fun deleteAll() {
        answerSavedDao.deleteAll()
    }
}