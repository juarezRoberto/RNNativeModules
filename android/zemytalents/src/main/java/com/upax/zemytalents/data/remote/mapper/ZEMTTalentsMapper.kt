package com.upax.zemytalents.data.remote.mapper

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.common.orFalse
import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.local.database.entity.talentscompleted.ZEMTTalentsCompletedEntity
import com.upax.zemytalents.data.remote.responses.ZEMTGetTalentsResponse
import com.upax.zemytalents.data.remote.responses.ZEMTTalentAttachmentResponse
import com.upax.zemytalents.data.remote.responses.ZEMTTalentResponse
import com.upax.zemytalents.data.remote.responses.ZEMTTalentsCompletedByListResponseItem
import com.upax.zemytalents.domain.models.ZEMTAttachmentType
import com.upax.zemytalents.domain.models.ZEMTCollaboratorTalentStatus
import com.upax.zemytalents.domain.models.ZEMTTalentAttachment
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.ZEMTTalents

internal fun ZEMTGetTalentsResponse.toTalentsUiModel(): ZEMTTalents {
    return ZEMTTalents(
        dominantTalents = this.dominant.orEmpty().toUiModels(),
        notDominantTalents = this.notDominant.orEmpty().toUiModels()
    )

}

internal fun List<ZEMTTalentResponse>.toUiModels(): List<ZEMTTalent> {
    return this.map { it.toUiModel() }
}

internal fun ZEMTTalentResponse.toUiModel(): ZEMTTalent {
    return ZEMTTalent(
        id = this.id.orZero(),
        name = this.name.orEmpty(),
        description = this.description.orEmpty(),
        attachment = this.attachments.orEmpty().map { it.toUiModel() }
    )
}

internal fun ZEMTTalentAttachmentResponse.toUiModel(): ZEMTTalentAttachment {
    return ZEMTTalentAttachment(
        type = when(type){
            1 -> ZEMTAttachmentType.VIDEO
            2 -> ZEMTAttachmentType.IMAGE
            3 -> ZEMTAttachmentType.LOTTIE
            4 -> ZEMTAttachmentType.ICON
            else -> ZEMTAttachmentType.NONE
        },
        url = this.url.orEmpty()
    )
}

internal fun ZEMTTalentsCompletedByListResponseItem.toRoomEntity(): ZEMTTalentsCompletedEntity {
    return ZEMTTalentsCompletedEntity(
        collaboratorId = this.talentId.orEmpty(),
        completed = this.completed.orFalse()
    )
}

internal fun ZEMTTalentsCompletedEntity.toModel(): ZEMTCollaboratorTalentStatus {
    return ZEMTCollaboratorTalentStatus(collaboratorId = this.collaboratorId, completed = this.completed)
}