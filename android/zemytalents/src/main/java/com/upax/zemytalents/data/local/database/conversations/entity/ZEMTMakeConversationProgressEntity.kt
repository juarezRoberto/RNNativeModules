package com.upax.zemytalents.data.local.database.conversations.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationStep

@Entity(
    tableName = "make_conversation_progress",
    primaryKeys = ["conversation_owner_id", "collaborator_id"],
    foreignKeys = [ForeignKey(
        entity = ZEMTCollaboratorInChargeEntity::class,
        parentColumns = ["collaborator_id"],
        childColumns = ["collaborator_id"]
    ), ForeignKey(
        entity = ZEMTConversationEntity::class,
        parentColumns = ["conversation_id"],
        childColumns = ["conversation_id"]
    ), ForeignKey(
        entity = ZEMTPhraseEntity::class,
        parentColumns = ["phrase_id", "collaborator_id", "conversation_id"],
        childColumns = ["phrase_id", "collaborator_id", "conversation_id"]
    )]
)
internal data class ZEMTMakeConversationProgressEntity(
    @ColumnInfo(name = "conversation_owner_id") val conversationOwnerId: String,
    @ColumnInfo(name = "collaborator_id") val collaboratorId: String,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "is_conversation_made") val isConversationMade: Boolean?,
    @ColumnInfo(name = "phrase_id") val phraseId: String?,
    @ColumnInfo("conversation_id") val conversationId: String?,
    @ColumnInfo(name = "current_step") val currentStep: ZEMTMakeConversationStep,
    @ColumnInfo(name = "start_date") val startDate: String,
)