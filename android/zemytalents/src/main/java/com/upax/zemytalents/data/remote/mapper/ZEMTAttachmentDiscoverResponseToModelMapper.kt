package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.remote.responses.ZEMTAttachmentDiscoverResponse
import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakAttachmentDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTBreakAttachmentDiscoverId

internal class ZEMTAttachmentDiscoverResponseToModelMapper :
    Function1<List<ZEMTAttachmentDiscoverResponse?>?, List<ZEMTBreakAttachmentDiscover>> {

    override fun invoke(attachments: List<ZEMTAttachmentDiscoverResponse?>?): List<ZEMTBreakAttachmentDiscover> {
        return attachments?.mapNotNull {
            if (it == null) return@mapNotNull null
            ZEMTBreakAttachmentDiscover(
                id = ZEMTBreakAttachmentDiscoverId(it.id.orZero()),
                url = it.url.orEmpty(),
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                type = it.type.orZero(),
                order = it.order.orZero()
            )
        } ?: emptyList()
    }

}