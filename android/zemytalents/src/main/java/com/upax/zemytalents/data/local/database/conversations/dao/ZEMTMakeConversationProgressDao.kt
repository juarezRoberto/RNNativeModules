package com.upax.zemytalents.data.local.database.conversations.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTMakeConversationProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ZEMTMakeConversationProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: ZEMTMakeConversationProgressEntity)

    @Query("SELECT * FROM make_conversation_progress WHERE conversation_owner_id = :conversationOwnerId AND collaborator_id = :collaboratorId")
    fun getProgress(
        conversationOwnerId: String,
        collaboratorId: String
    ): Flow<ZEMTMakeConversationProgressEntity?>

    @Query("SELECT * FROM make_conversation_progress WHERE conversation_owner_id = :conversationOwnerId AND collaborator_id = :collaboratorId")
   suspend fun getProgressNotObservable( conversationOwnerId: String, collaboratorId: String): ZEMTMakeConversationProgressEntity?

   @Query("DELETE FROM make_conversation_progress WHERE conversation_owner_id = :conversationOwnerId AND collaborator_id = :collaboratorId")
   suspend fun deleteProgressById(conversationOwnerId: String, collaboratorId: String)
}