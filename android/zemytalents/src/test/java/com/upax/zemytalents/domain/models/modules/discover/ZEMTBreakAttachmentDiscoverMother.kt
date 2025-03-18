package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTBreakAttachmentDiscoverId
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTBreakAttachmentDiscoverMother {

    fun apply(
        id: ZEMTBreakAttachmentDiscoverId = ZEMTBreakAttachmentDiscoverId(ZEMTRandomValuesUtil.getInt()),
        url: String = ZEMTRandomValuesUtil.getString(),
        name: String = ZEMTRandomValuesUtil.getString(),
        description: String = ZEMTRandomValuesUtil.getString(),
        type: Int = ZEMTRandomValuesUtil.getInt(),
        order: Int = ZEMTRandomValuesUtil.getInt()
    ): ZEMTBreakAttachmentDiscover {
        return ZEMTBreakAttachmentDiscover(
            id = id,
            url = url,
            name = name,
            description = description,
            type = type,
            order = order
        )
    }

    fun random() = apply()

}