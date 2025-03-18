package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import kotlinx.coroutines.flow.Flow

internal interface ZEMTGroupQuestionsDiscoverRepository {

    val currentGroupQuestions: Flow<ZEMTGroupQuestionsDiscover?>

    val totalGroupQuestionsAnswered: Flow<Int>

    suspend fun getTotalGroupQuestions(): Int

    suspend fun getNextGroupQuestionIndex(): Int?

    suspend fun updateGroupQuestionIndex(index: Int)

}