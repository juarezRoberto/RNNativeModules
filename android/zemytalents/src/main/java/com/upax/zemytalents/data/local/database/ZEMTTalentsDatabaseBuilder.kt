package com.upax.zemytalents.data.local.database

import android.content.Context
import androidx.room.Room
import com.prometeo.keymanager.expose.ZKMSQLDatabaseUtils
import com.upax.zemytalents.common.ZEMTEnvironment
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

internal object ZEMTTalentsDatabaseBuilder {

    private const val DB_NAME = "zetalents.db"

    @Volatile
    private var INSTANCE: ZEMTTalentsDatabase? = null

    fun getInstance(context: Context): ZEMTTalentsDatabase {
        return INSTANCE ?: synchronized(this) {
            val appContext = context.applicationContext
            val oldKey = ""
            val newKey = ZKMSQLDatabaseUtils.getSQLDefaultKey()
            if (ZEMTEnvironment.encryptDatabase()) {
                if (ZKMSQLDatabaseUtils.existDatabase(appContext, DB_NAME)) {
                    val isEncrypted = ZKMSQLDatabaseUtils.isEncrypted(appContext, DB_NAME)
                    if (isEncrypted) {
                        when {
                            ZKMSQLDatabaseUtils.validatePassword(appContext, DB_NAME, newKey) -> {
                                INSTANCE = buildRoom(appContext, newKey)
                                return INSTANCE!!
                            }

                            ZKMSQLDatabaseUtils.validatePassword(appContext, DB_NAME, oldKey) -> {
                                ZKMSQLDatabaseUtils.changePassword(
                                    appContext,
                                    DB_NAME,
                                    oldKey,
                                    newKey
                                )
                            }

                            else -> {
                                ZKMSQLDatabaseUtils.delete(appContext, DB_NAME)
                            }
                        }
                    }
                    ZKMSQLDatabaseUtils.encryptDatabase(appContext, DB_NAME, newKey)
                    INSTANCE = buildRoom(appContext, newKey)
                } else {
                    INSTANCE = buildRoom(appContext, newKey)
                }
            } else {
                if (ZKMSQLDatabaseUtils.isEncrypted(appContext, DB_NAME)) {
                    when {
                        ZKMSQLDatabaseUtils.validatePassword(appContext, DB_NAME, newKey) -> {
                            ZKMSQLDatabaseUtils.decryptDatabase(appContext, DB_NAME, newKey)
                        }

                        ZKMSQLDatabaseUtils.validatePassword(appContext, DB_NAME, oldKey) -> {
                            ZKMSQLDatabaseUtils.decryptDatabase(appContext, DB_NAME, oldKey)
                        }

                        else -> {
                            ZKMSQLDatabaseUtils.delete(appContext, DB_NAME)
                        }
                    }
                }
                INSTANCE = buildRoomDebug(appContext)
            }
            INSTANCE!!
        }
    }

    private fun buildRoom(context: Context, passphrase: String): ZEMTTalentsDatabase {
        val factory = SupportFactory(SQLiteDatabase.getBytes(passphrase.toCharArray()))
        return Room.databaseBuilder(context, ZEMTTalentsDatabase::class.java, DB_NAME)
            .addMigrations(
                ZEMTTalentsDatabase.MIGRATION_1_2,
                ZEMTTalentsDatabase.MIGRATION_2_3,
                ZEMTTalentsDatabase.MIGRATION_3_4
            )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigrationOnDowngrade()
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun buildRoomDebug(context: Context): ZEMTTalentsDatabase {
        return Room.databaseBuilder(context, ZEMTTalentsDatabase::class.java, DB_NAME)
            .addMigrations(
                ZEMTTalentsDatabase.MIGRATION_1_2,
                ZEMTTalentsDatabase.MIGRATION_2_3,
                ZEMTTalentsDatabase.MIGRATION_3_4
            )
            .fallbackToDestructiveMigrationOnDowngrade()
            .fallbackToDestructiveMigration()
            .build()
    }

}