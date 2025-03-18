package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTBreakDiscoverMother {

    fun apply(
        indexGroupQuestion: ZEMTGroupQuestionIndexDiscover =
            ZEMTGroupQuestionIndexDiscover(ZEMTRandomValuesUtil.getInt()),
        text: String = ZEMTRandomValuesUtil.getString(),
        attachments: List<ZEMTBreakAttachmentDiscover> = ZEMTRandomValuesUtil.getRandomIntRange()
            .map { ZEMTBreakAttachmentDiscoverMother.random() }
    ): ZEMTBreakDiscover {
        return ZEMTBreakDiscover(
            indexGroupQuestion = indexGroupQuestion,
            text = text,
            attachments = attachments
        )
    }

    fun random() = apply()

}