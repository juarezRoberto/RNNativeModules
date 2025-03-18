package com.upax.zemytalents.domain.models

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTTalentAttachmentMother {

    fun apply(
        type: ZEMTAttachmentType = ZEMTAttachmentType.entries.random(),
        url: String = ZEMTRandomValuesUtil.getString()
    ): ZEMTTalentAttachment {
        return ZEMTTalentAttachment(
            type = type,
            url = url
        )
    }

    fun random() = apply()

}