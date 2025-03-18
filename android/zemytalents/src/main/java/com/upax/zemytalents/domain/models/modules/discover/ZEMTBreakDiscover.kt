package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover

internal data class ZEMTBreakDiscover(
    val indexGroupQuestion: ZEMTGroupQuestionIndexDiscover,
    val text: String,
    val attachments: List<ZEMTBreakAttachmentDiscover>
)