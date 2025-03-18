package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import kotlinx.coroutines.flow.Flow

internal interface ZEMTSurveyDiscoverBreaksRepository {

    fun getNextBreak(): Flow<ZEMTBreakDiscover?>

    suspend fun markBreakAsSeen(groupQuestionIndex: ZEMTGroupQuestionIndexDiscover)

}