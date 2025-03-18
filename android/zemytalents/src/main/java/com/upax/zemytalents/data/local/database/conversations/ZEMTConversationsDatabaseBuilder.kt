package com.upax.zemytalents.data.local.database.conversations

import android.content.Context
import androidx.room.Room
import com.prometeo.keymanager.expose.ZKMSQLDatabaseUtils
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

internal object ZEMTConversationsDatabaseBuilder {
    private const val DATABASE_NAME = "zemt_conversations.db"

    @Volatile
    private var instance: ZEMTConversationsDatabase? = null

    fun getInstance(context: Context): ZEMTConversationsDatabase {
        return instance ?: synchronized(this) {
            val appContext = context.applicationContext
            val oldKey = ""
            val newKey = ZKMSQLDatabaseUtils.getSQLDefaultKey()
            if (false) {
                if (ZKMSQLDatabaseUtils.existDatabase(appContext, DATABASE_NAME)) {
                    val isEncrypted = ZKMSQLDatabaseUtils.isEncrypted(appContext, DATABASE_NAME)
                    if (isEncrypted) {
                        when {
                            ZKMSQLDatabaseUtils.validatePassword(
                                appContext,
                                DATABASE_NAME,
                                newKey
                            ) -> {
                                instance = buildRoom(appContext, newKey)
                                return instance!!
                            }

                            ZKMSQLDatabaseUtils.validatePassword(
                                appContext,
                                DATABASE_NAME,
                                oldKey
                            ) -> {
                                ZKMSQLDatabaseUtils.changePassword(
                                    appContext,
                                    DATABASE_NAME,
                                    oldKey,
                                    newKey
                                )
                            }

                            else -> {
                                ZKMSQLDatabaseUtils.delete(appContext, DATABASE_NAME)
                            }
                        }
                    }
                    ZKMSQLDatabaseUtils.encryptDatabase(appContext, DATABASE_NAME, newKey)
                    instance = buildRoom(appContext, newKey)
                } else {
                    instance = buildRoom(appContext, newKey)
                }
            } else {
                if (ZKMSQLDatabaseUtils.isEncrypted(appContext, DATABASE_NAME)) {
                    when {
                        ZKMSQLDatabaseUtils.validatePassword(appContext, DATABASE_NAME, newKey) -> {
                            ZKMSQLDatabaseUtils.decryptDatabase(appContext, DATABASE_NAME, newKey)
                        }

                        ZKMSQLDatabaseUtils.validatePassword(appContext, DATABASE_NAME, oldKey) -> {
                            ZKMSQLDatabaseUtils.decryptDatabase(appContext, DATABASE_NAME, oldKey)
                        }

                        else -> {
                            ZKMSQLDatabaseUtils.delete(appContext, DATABASE_NAME)
                        }
                    }
                }
                instance = buildRoomDebug(appContext)
            }
            instance!!
        }
    }


    private fun buildRoom(context: Context, passphrase: String): ZEMTConversationsDatabase {
        val factory = SupportFactory(SQLiteDatabase.getBytes(passphrase.toCharArray()))
        return Room.databaseBuilder(context, ZEMTConversationsDatabase::class.java, DATABASE_NAME)
            .addMigrations()
            .openHelperFactory(factory)
            .fallbackToDestructiveMigrationOnDowngrade()
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun buildRoomDebug(context: Context): ZEMTConversationsDatabase {
        return Room.databaseBuilder(context, ZEMTConversationsDatabase::class.java, DATABASE_NAME)
            .addMigrations()
            .fallbackToDestructiveMigrationOnDowngrade()
            .fallbackToDestructiveMigration()
            .build()
    }
}