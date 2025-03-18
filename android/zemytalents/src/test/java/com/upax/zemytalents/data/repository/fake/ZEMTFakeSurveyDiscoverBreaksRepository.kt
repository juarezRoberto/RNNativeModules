package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverBreaksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class ZEMTFakeSurveyDiscoverBreaksRepository(
    breaks: List<ZEMTBreakDiscover> = emptyList()
) : ZEMTSurveyDiscoverBreaksRepository {

    private val breaksState = MutableStateFlow<List<ZEMTBreakDiscover?>>(breaks)

    override fun getNextBreak(): Flow<ZEMTBreakDiscover?> = breaksState.map {
        it.firstOrNull()
    }

    override suspend fun markBreakAsSeen(groupQuestionIndex: ZEMTGroupQuestionIndexDiscover) {
        val newBreaks = breaksState.value.map {
            if (it?.indexGroupQuestion == groupQuestionIndex) null else it
        }
        breaksState.update { newBreaks }
    }

}