package com.upax.zemytalents.data.local.database.conversations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTPhraseEntity

@Dao
internal interface ZEMTPhraseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(phrases: List<ZEMTPhraseEntity>)

    @Query("SELECT * FROM phrases WHERE conversation_id = :conversationId AND collaborator_id = :collaboratorId")
    suspend fun getPhrases(conversationId: String, collaboratorId: String): List<ZEMTPhraseEntity>
}