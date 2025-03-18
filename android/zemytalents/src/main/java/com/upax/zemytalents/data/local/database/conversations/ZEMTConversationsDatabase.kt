package com.upax.zemytalents.data.local.database.conversations

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upax.zemytalents.data.local.database.conversations.dao.ZEMTCollaboratorInChargeDao
import com.upax.zemytalents.data.local.database.conversations.dao.ZEMTConversationDao
import com.upax.zemytalents.data.local.database.conversations.dao.ZEMTMakeConversationProgressDao
import com.upax.zemytalents.data.local.database.conversations.dao.ZEMTPhraseDao
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTCollaboratorInChargeEntity
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTConversationEntity
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTMakeConversationProgressEntity
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTPhraseEntity

@Database(
    entities = [ZEMTCollaboratorInChargeEntity::class, ZEMTConversationEntity::class, ZEMTPhraseEntity::class, ZEMTMakeConversationProgressEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class ZEMTConversationsDatabase : RoomDatabase() {
    abstract fun collaboratorInChargeDao(): ZEMTCollaboratorInChargeDao
    abstract fun conversationDao(): ZEMTConversationDao
    abstract fun phraseDao(): ZEMTPhraseDao
    abstract fun makeConversationProgressDao(): ZEMTMakeConversationProgressDao
}