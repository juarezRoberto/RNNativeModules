package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil


internal object ZEMTGroupQuestionsMother {

    fun apply(
        leftQuestion: ZEMTQuestionDiscover = ZEMTQuestionMother.random(),
        rightQuestion: ZEMTQuestionDiscover = ZEMTQuestionMother.random(),
        index: ZEMTGroupQuestionIndexDiscover = ZEMTGroupQuestionIndexDiscover(ZEMTRandomValuesUtil.getInt()),
    ): ZEMTGroupQuestionsDiscover {
        return ZEMTGroupQuestionsDiscover(
            index = index,
            leftQuestion = leftQuestion,
            rightQuestion = rightQuestion
        )
    }

    fun random() = apply()

}