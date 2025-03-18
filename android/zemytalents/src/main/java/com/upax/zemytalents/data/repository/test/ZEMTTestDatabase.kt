package com.upax.zemytalents.data.repository.test

import android.content.Context
import androidx.room.Room
import com.upax.zemytalents.data.local.database.ZEMTTalentsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

internal object ZEMTTestDatabase {

    @Volatile
    private var INSTANCE: ZEMTTalentsDatabase? = null

    fun getInstance(context: Context): ZEMTTalentsDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context, ZEMTTalentsDatabase::class.java)
                .setTransactionExecutor(Dispatchers.Main.asExecutor())
                .allowMainThreadQueries()
                .build()
            INSTANCE!!
        }
    }

}