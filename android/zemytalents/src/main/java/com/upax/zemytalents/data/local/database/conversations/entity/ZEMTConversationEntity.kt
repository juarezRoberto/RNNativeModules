package com.upax.zemytalents.data.local.database.conversations.entity

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversation")
data class ZEMTConversationEntity(
    @PrimaryKey @ColumnInfo(name = "conversation_id") val conversationId: String,
    val name: String,
    val description: String,
    val order: Int,
    @DrawableRes val icon: Int,
    @ColumnInfo(name = "lottie_url") val lottieUrl: String,
)
