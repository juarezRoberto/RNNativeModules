package com.upax.zemytalents.domain.models.conversations

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTCollaboratorInChargeMother {

    fun apply(
        collaboratorId: String = ZEMTRandomValuesUtil.getString(),
        name: String = ZEMTRandomValuesUtil.getString(),
        photoUrl: String = ZEMTRandomValuesUtil.getString(),
        talentsCompleted: Boolean = ZEMTRandomValuesUtil.getBoolean()
    ): ZEMTCollaboratorInCharge {
        return ZEMTCollaboratorInCharge(
            collaboratorId = collaboratorId,
            name = name,
            photoUrl = photoUrl,
            talentsCompleted = talentsCompleted
        )
    }

    fun random() = apply()

}