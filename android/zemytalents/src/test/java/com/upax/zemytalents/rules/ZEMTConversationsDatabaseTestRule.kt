package com.upax.zemytalents.rules

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.upax.zemytalents.data.local.database.conversations.ZEMTConversationsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import org.junit.rules.TestWatcher
import org.junit.runner.Description

internal class ZEMTConversationsDatabaseTestRule: TestWatcher() {

    private var database: ZEMTConversationsDatabase? = null

    override fun starting(description: Description) {
        super.starting(description)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ZEMTConversationsDatabase::class.java)
            .setTransactionExecutor(Dispatchers.Main.asExecutor())
            .allowMainThreadQueries()
            .build()
    }

    override fun finished(description: Description) {
        super.finished(description)
        database?.clearAllTables()
        database?.close()
        database = null
    }

    fun collaboratorsInChargeDao() = database!!.collaboratorInChargeDao()

}