package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTBreakAttachmentDiscoverId

internal data class ZEMTBreakAttachmentDiscover(
    val id: ZEMTBreakAttachmentDiscoverId,
    val url: String,
    val name: String,
    val description: String,
    val type: Int,
    val order: Int
)