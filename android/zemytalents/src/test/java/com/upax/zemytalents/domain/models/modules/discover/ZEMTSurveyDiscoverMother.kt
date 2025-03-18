package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTSurveyDiscoverMother {

    fun apply(
        id: ZEMTSurveyId = ZEMTSurveyId(ZEMTRandomValuesUtil.getInt().toString()),
        breaks: List<ZEMTBreakDiscover> = ZEMTRandomValuesUtil.getRandomIntRange().map {
            ZEMTBreakDiscoverMother.random()
        },
        groupQuestions: List<ZEMTGroupQuestionsDiscover> = ZEMTRandomValuesUtil.getRandomIntRange()
            .map { ZEMTGroupQuestionsDiscoverMother.random() }
    ): ZEMTSurveyDiscover {
        return ZEMTSurveyDiscover(
            id = id,
            breaks = breaks,
            groupQuestions = groupQuestions
        )
    }

    fun random() = apply()

}