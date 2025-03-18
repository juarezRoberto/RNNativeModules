package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTBreakDiscoverDao
import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverBreaksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ZEMTRoomSurveyDiscoverBreaksRepository(
    private val breakDao: ZEMTBreakDiscoverDao
) : ZEMTSurveyDiscoverBreaksRepository {

    override fun getNextBreak(): Flow<ZEMTBreakDiscover?> {
        return breakDao.getNextBreak().map {
            it?.let {
                ZEMTBreakDiscover(
                    indexGroupQuestion = ZEMTGroupQuestionIndexDiscover(it.groupQuestionIndex),
                    text = it.text,
                    attachments = emptyList()
                )
            }
        }
    }

    override suspend fun markBreakAsSeen(groupQuestionIndex: ZEMTGroupQuestionIndexDiscover) {
        breakDao.updateSeenBreak(groupQuestionIndex.value)
    }

}