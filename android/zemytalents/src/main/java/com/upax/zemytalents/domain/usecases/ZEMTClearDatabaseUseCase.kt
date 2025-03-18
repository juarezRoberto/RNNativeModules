package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.local.database.ZEMTTalentsDatabase
import com.upax.zemytalents.data.local.database.conversations.ZEMTConversationsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ZEMTClearDatabaseUseCase(
    private val database: ZEMTTalentsDatabase,
    private val conversationsDatabase: ZEMTConversationsDatabase
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        database.clearAllTables()
        conversationsDatabase.clearAllTables()
    }
}