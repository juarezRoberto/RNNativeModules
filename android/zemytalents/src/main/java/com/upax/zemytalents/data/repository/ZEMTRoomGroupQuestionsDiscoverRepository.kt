package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTAnswerSavedDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTQuestionDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyDiscoverDao
import com.upax.zemytalents.data.local.database.mapper.ZEMTListQuestionWithAnswersEntityToModelMapper
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import com.upax.zemytalents.domain.repositories.ZEMTGroupQuestionsDiscoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class ZEMTRoomGroupQuestionsDiscoverRepository(
    private val surveyDao: ZEMTSurveyDiscoverDao,
    private val questionDao: ZEMTQuestionDiscoverDao,
    private val answerSavedDao: ZEMTAnswerSavedDiscoverDao,
    private val listQuestionWithAnswersToModelMapper: ZEMTListQuestionWithAnswersEntityToModelMapper
) : ZEMTGroupQuestionsDiscoverRepository {

    override val currentGroupQuestions: Flow<ZEMTGroupQuestionsDiscover?>
        get() = questionDao.getCurrentGroupQuestions()
            .map(listQuestionWithAnswersToModelMapper)
            .distinctUntilChanged()
            .onEach { groupQuestion ->
                groupQuestion?.index?.let {
                    questionDao.updateLastSeen(it.value, lastSeen = System.currentTimeMillis())
                }
            }

    override val totalGroupQuestionsAnswered: Flow<Int>
        get() = answerSavedDao.getTotalAnswers()

    override suspend fun getTotalGroupQuestions(): Int {
        return questionDao.getTotalGroupQuestions()
    }

    override suspend fun getNextGroupQuestionIndex(): Int? {
        return questionDao.getNextGroupQuestionIndex()
    }

    override suspend fun updateGroupQuestionIndex(index: Int) {
        surveyDao.updateCurrentGroupQuestionIndex(index = index)
    }

}