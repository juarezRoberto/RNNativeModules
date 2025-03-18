package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.remote.responses.ZEMTBreakDiscoverResponse
import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover

internal class ZEMTBreakDiscoverResponseToModelMapper(
    private val attachmentResponseToModelMapper: ZEMTAttachmentDiscoverResponseToModelMapper
): Function1<List<ZEMTBreakDiscoverResponse?>?, List<ZEMTBreakDiscover>> {


    override fun invoke(breaksResponse: List<ZEMTBreakDiscoverResponse?>?): List<ZEMTBreakDiscover> {
        return breaksResponse?.mapNotNull {
            if (it == null) return@mapNotNull null
            ZEMTBreakDiscover(
                indexGroupQuestion = ZEMTGroupQuestionIndexDiscover(it.indexQuestionGroup.orZero()),
                text = it.text.orEmpty(),
                attachments = attachmentResponseToModelMapper.invoke(it.attachments)
            )
        } ?: emptyList()
    }

}