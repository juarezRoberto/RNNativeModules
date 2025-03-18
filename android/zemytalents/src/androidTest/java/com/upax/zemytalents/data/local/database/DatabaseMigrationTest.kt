package com.upax.zemytalents.data.local.database

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseMigrationTest {

    private val allMigrations = arrayOf(
        ZEMTTalentsDatabase.MIGRATION_1_2,
        ZEMTTalentsDatabase.MIGRATION_2_3,
        ZEMTTalentsDatabase.MIGRATION_3_4
    )

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        ZEMTTalentsDatabase::class.java.canonicalName!!,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration3to4wasSuccessful() {
        helper.createDatabase(DB_NAME, 3).apply {
            execSQL("INSERT INTO SurveyDiscoverBreak VALUES (10, 11)")
            close()
        }
        val db = helper.runMigrationsAndValidate(
            DB_NAME,
            4,
            true,
            ZEMTTalentsDatabase.MIGRATION_3_4
        )
        db.query("SELECT * FROM SurveyDiscoverBreak").apply {
            assertTrue(moveToFirst())
            assertEquals("", getString(getColumnIndex("text")))
            assertEquals(0, getInt(getColumnIndex("seen")))
        }
    }

    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        // Create earliest version of the database.
        helper.createDatabase(DB_NAME, 1).apply { close() }

        // Open latest version of the database. Room validates the schema
        // once all migrations execute.
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            ZEMTTalentsDatabase::class.java,
            DB_NAME
        ).addMigrations(*allMigrations).build().apply {
            openHelper.writableDatabase.close()
        }
    }

    companion object {
        private const val DB_NAME = "zetalents"
    }

}