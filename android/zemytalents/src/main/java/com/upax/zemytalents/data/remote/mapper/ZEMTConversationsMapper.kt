package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orFalse
import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTCollaboratorInChargeEntity
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTConversationEntity
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTMakeConversationProgressEntity
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTPhraseEntity
import com.upax.zemytalents.data.local.icons.ZEMTIconParser
import com.upax.zemytalents.data.remote.responses.ZEMTCollaboratorResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTConversationAttachmentResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTConversationHistoryResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTConversationResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTPhraseHistoryResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTPhraseResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTTalentHistoryResponse
import com.upax.zemytalents.domain.models.ZEMTAttachmentType
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationHistory
import com.upax.zemytalents.domain.models.conversations.ZEMTPhrase
import com.upax.zemytalents.domain.models.conversations.ZEMTPhraseHistory
import com.upax.zemytalents.domain.models.conversations.ZEMTTalentHistory
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationProgress

internal object ZEMTConversationsMapper {

    fun ZEMTCollaboratorResponse.toModel(): ZEMTCollaboratorInCharge {
        return ZEMTCollaboratorInCharge(
            name = this.shortName.orEmpty(),
            photoUrl = this.photo.orEmpty(),
            collaboratorId = this.id.orEmpty(),
            talentsCompleted = false
        )
    }

    fun ZEMTCollaboratorResponse.toRoomEntity(leaderId: String): ZEMTCollaboratorInChargeEntity =
        ZEMTCollaboratorInChargeEntity(
            collaboratorId = this.id.orEmpty(),
            name = this.shortName.orEmpty(),
            photoUrl = this.photo.orEmpty(),
            talentsCompleted = false,
            leaderId = leaderId
        )

    fun ZEMTCollaboratorInCharge.toRoomEntity(leaderId: String): ZEMTCollaboratorInChargeEntity =
        ZEMTCollaboratorInChargeEntity(
            collaboratorId = this.collaboratorId,
            name = this.name,
            photoUrl = this.photoUrl,
            talentsCompleted = this.talentsCompleted,
            leaderId = leaderId
        )

    fun ZEMTConversationEntity.toModel(): ZEMTConversation {
        return ZEMTConversation(
            conversationId = this.conversationId,
            name = this.name,
            description = this.description,
            order = this.order,
            icon = icon,
            lottieUrl = lottieUrl
        )
    }

    fun ZEMTConversationResponse?.toModel(): ZEMTConversation {
        return ZEMTConversation(
            conversationId = this?.conversationId.orEmpty(),
            name = this?.name.orEmpty(),
            description = this?.description.orEmpty(),
            order = this?.order.orZero(),
            icon = getIcon(this?.attachments.orEmpty(), 0),
            lottieUrl = getLottieUrl(this?.attachments.orEmpty())
        )
    }

    fun ZEMTConversationResponse.toRoomEntity(index: Int): ZEMTConversationEntity {
        return ZEMTConversationEntity(
            conversationId = this.conversationId.orEmpty(),
            name = this.name.orEmpty(),
            description = this.description.orEmpty(),
            order = this.order ?: -1,
            icon = getIcon(this.attachments.orEmpty(), index),
            lottieUrl = getLottieUrl(this.attachments.orEmpty())
        )
    }

    fun ZEMTPhraseResponse.toRoomEntity(
        conversationId: String,
        collaboratorId: String
    ): ZEMTPhraseEntity {
        return ZEMTPhraseEntity(
            phraseId = this.id,
            conversationId = conversationId,
            phrase = this.phrase,
            collaboratorId = collaboratorId
        )
    }

    fun ZEMTPhraseEntity.toModel(): ZEMTPhrase {
        return ZEMTPhrase(
            id = this.phraseId,
            phrase = this.phrase,
            conversationId = this.conversationId
        )
    }

    fun ZEMTPhrase.toRoomEntity(conversationId: String, collaboratorId: String): ZEMTPhraseEntity {
        return ZEMTPhraseEntity(
            phraseId = this.id,
            conversationId = conversationId,
            phrase = this.phrase,
            collaboratorId = collaboratorId
        )
    }

    fun ZEMTMakeConversationProgressEntity.toModel(): ZEMTMakeConversationProgress {
        return ZEMTMakeConversationProgress(
            conversationOwnerId = this.conversationOwnerId,
            collaboratorId = this.collaboratorId,
            comment = this.comment,
            isConversationMade = this.isConversationMade,
            phraseId = this.phraseId.orEmpty(),
            conversationId = this.conversationId.orEmpty(),
            currentStep = this.currentStep
        )
    }

    fun ZEMTMakeConversationProgress.toRoomEntity(
        conversationOwnerId: String,
        collaboratorId: String
    ): ZEMTMakeConversationProgressEntity {
        return ZEMTMakeConversationProgressEntity(
            conversationOwnerId = conversationOwnerId,
            collaboratorId = collaboratorId,
            comment = this.comment,
            isConversationMade = this.isConversationMade,
            phraseId = this.phraseId.ifEmpty { null },
            conversationId = this.conversationId.ifEmpty { null },
            currentStep = this.currentStep,
            startDate = this.startDate
        )
    }

    private fun getLottieUrl(attachments: List<ZEMTConversationAttachmentResponse>): String {
        val lottie = attachments.find { it.type == ZEMTAttachmentType.LOTTIE.id }
        return lottie?.url.orEmpty()
    }

    private fun getIcon(attachments: List<ZEMTConversationAttachmentResponse>, index: Int): Int {
        val iconId = attachments.find { it.type == ZEMTAttachmentType.ICON.id }
        val icon = ZEMTIconParser.entries.find { it.iconName == iconId?.url }
        val iconIdResource = ZEMTIconParser.parseIcon(icon?.iconId ?: -1, index)
        return iconIdResource
    }

    fun ZEMTConversationHistoryResponse?.toModel(): ZEMTConversationHistory {
        return ZEMTConversationHistory(
            bossId = this?.bossId.orEmpty(),
            phrase = this?.phrase.toModel(),
            realized = this?.realized.orFalse(),
            startDate = this?.startDate.orEmpty(),
            endDate = this?.endDate.orEmpty(),
            comment = this?.comment.orEmpty(),
            device = this?.device.orEmpty(),
            platform = this?.platform.orEmpty()
        )
    }

    fun ZEMTPhraseHistoryResponse?.toModel(): ZEMTPhraseHistory {
        return ZEMTPhraseHistory(
            id = this?.id.orZero(),
            description = this?.description.orEmpty(),
            talent = this?.talent.toModel(),
            conversation = this?.conversation.toModel(),
            order = this?.order.orZero()
        )
    }

    fun ZEMTTalentHistoryResponse?.toModel(): ZEMTTalentHistory {
        return ZEMTTalentHistory(
            talentId = this?.talentId.orZero(),
            description = this?.description.orEmpty()
        )
    }
}