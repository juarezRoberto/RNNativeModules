package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionMother
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.domain.repositories.ZEMTGroupQuestionsDiscoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTFakeGroupQuestionsDiscoverRepository(
    private val totalGroupQuestions: Int = 100,
    private val _totalGroupQuestionsAnswered: Int = 50,
    private val _currentGroupQuestions: ZEMTGroupQuestionsDiscover = ZEMTGroupQuestionsDiscover(
        index = ZEMTGroupQuestionIndexDiscover(0),
        leftQuestion = ZEMTQuestionMother.random(),
        rightQuestion = ZEMTQuestionMother.random()
    )
) : ZEMTGroupQuestionsDiscoverRepository {

    override val currentGroupQuestions: Flow<ZEMTGroupQuestionsDiscover?>
        get() = flow { emit(_currentGroupQuestions) }

    override val totalGroupQuestionsAnswered: Flow<Int>
        get() = flow { emit(_totalGroupQuestionsAnswered) }

    override suspend fun getTotalGroupQuestions(): Int {
        return totalGroupQuestions
    }

    override suspend fun updateGroupQuestionIndex(index: Int) {

    }

    override suspend fun getNextGroupQuestionIndex(): Int {
        return 0
    }
}