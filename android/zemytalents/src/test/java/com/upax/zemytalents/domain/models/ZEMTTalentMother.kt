package com.upax.zemytalents.domain.models

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTTalentMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        name: String = ZEMTRandomValuesUtil.getString(),
        description: String = ZEMTRandomValuesUtil.getString(),
        isTempTalent: Boolean = ZEMTRandomValuesUtil.getBoolean(),
        attachment: List<ZEMTTalentAttachment> = ZEMTRandomValuesUtil.getRandomIntRange().map {
            ZEMTTalentAttachmentMother.random()
        }
    ): ZEMTTalent {
        return ZEMTTalent(
            id = id,
            name = name,
            description = description,
            isTempTalent = isTempTalent,
            attachment
        )
    }

    fun random() = apply()

}