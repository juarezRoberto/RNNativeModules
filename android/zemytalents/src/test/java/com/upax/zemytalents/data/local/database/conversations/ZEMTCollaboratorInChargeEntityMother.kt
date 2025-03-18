package com.upax.zemytalents.data.local.database.conversations

import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTCollaboratorInChargeEntity
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTCollaboratorInChargeEntityMother {

    fun apply(
        collaboratorId: String = ZEMTRandomValuesUtil.getString(),
        leaderId: String = ZEMTRandomValuesUtil.getString(),
        name: String = ZEMTRandomValuesUtil.getString(),
        photoUrl: String = ZEMTRandomValuesUtil.getString(),
        talentsCompleted: Boolean = ZEMTRandomValuesUtil.getBoolean()
    ): ZEMTCollaboratorInChargeEntity {
        return ZEMTCollaboratorInChargeEntity(
            collaboratorId = collaboratorId,
            leaderId = leaderId,
            name = name,
            photoUrl = photoUrl,
            talentsCompleted = talentsCompleted
        )
    }

    fun random() = apply()

}