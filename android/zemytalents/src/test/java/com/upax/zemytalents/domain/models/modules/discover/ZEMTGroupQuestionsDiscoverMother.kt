package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTGroupQuestionsDiscoverMother {

    fun apply(
        index: ZEMTGroupQuestionIndexDiscover = ZEMTGroupQuestionIndexDiscover(ZEMTRandomValuesUtil.getInt()),
        leftQuestion: ZEMTQuestionDiscover = ZEMTQuestionDiscoverMother.random(),
        rightQuestion: ZEMTQuestionDiscover = ZEMTQuestionDiscoverMother.random()
    ): ZEMTGroupQuestionsDiscover {
        return ZEMTGroupQuestionsDiscover(
            index = index,
            leftQuestion = leftQuestion,
            rightQuestion = rightQuestion
        )
    }

    fun random() = apply()

}