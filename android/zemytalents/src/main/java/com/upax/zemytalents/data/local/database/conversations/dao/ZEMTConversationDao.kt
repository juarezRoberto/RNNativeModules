package com.upax.zemytalents.data.local.database.conversations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTConversationEntity

@Dao
internal interface ZEMTConversationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(conversations: List<ZEMTConversationEntity>)

    @Query("SELECT * FROM conversation")
    fun getConversations(): List<ZEMTConversationEntity>

    @Query("SELECT * FROM conversation WHERE conversation_id = :conversationId")
    fun getConversation(conversationId: String): ZEMTConversationEntity?
}