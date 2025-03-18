package com.upax.zemytalents.data.local.database.conversations.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "phrases",
    foreignKeys = [ForeignKey(
        entity = ZEMTConversationEntity::class,
        parentColumns = ["conversation_id"],
        childColumns = ["conversation_id"]
    )],
    primaryKeys = ["phrase_id", "collaborator_id", "conversation_id"]
)
data class ZEMTPhraseEntity(
    @ColumnInfo(name = "phrase_id") val phraseId: String,
    @ColumnInfo(name = "collaborator_id") val collaboratorId: String,
    @ColumnInfo(name = "conversation_id") val conversationId: String,
    val phrase: String,
)
