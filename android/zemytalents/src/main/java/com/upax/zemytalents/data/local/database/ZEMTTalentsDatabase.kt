package com.upax.zemytalents.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.upax.zemytalents.data.local.database.dao.ZEMTAnswerOptionDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTAnswerSavedDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTBreakDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTModuleDao
import com.upax.zemytalents.data.local.database.dao.ZEMTModuleMultimediaDao
import com.upax.zemytalents.data.local.database.dao.ZEMTQuestionDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyApplyDao
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyConfirmAnswerSavedDao
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTTalentsCompletedDao
import com.upax.zemytalents.data.local.database.entity.apply.ZEMTSurveyApplyAnswerSavedEntity
import com.upax.zemytalents.data.local.database.entity.confirm.ZEMTSurveyConfirmAnswerSavedEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerSavedDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTBreakDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTSurveyDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleEntity
import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleMultimediaEntity
import com.upax.zemytalents.data.local.database.entity.talentscompleted.ZEMTTalentsCompletedEntity

@Database(
    entities = [
        ZEMTSurveyDiscoverEntity::class,
        ZEMTQuestionDiscoverEntity::class,
        ZEMTAnswerOptionDiscoverEntity::class,
        ZEMTAnswerSavedDiscoverEntity::class,
        ZEMTBreakDiscoverEntity::class,
        ZEMTModuleEntity::class,
        ZEMTModuleMultimediaEntity::class,
        ZEMTTalentsCompletedEntity::class,
        ZEMTSurveyConfirmAnswerSavedEntity::class,
        ZEMTSurveyApplyAnswerSavedEntity::class
    ],
    version = 4,
    exportSchema = true
)
internal abstract class ZEMTTalentsDatabase : RoomDatabase() {

    abstract fun surveyDiscoverDao(): ZEMTSurveyDiscoverDao
    abstract fun questionDiscoverDao(): ZEMTQuestionDiscoverDao
    abstract fun answerOptionDiscoverDao(): ZEMTAnswerOptionDiscoverDao
    abstract fun answerSavedDiscoverDao(): ZEMTAnswerSavedDiscoverDao
    abstract fun breakDiscoverDao(): ZEMTBreakDiscoverDao
    abstract fun moduleDao(): ZEMTModuleDao
    abstract fun moduleMultimediaDao(): ZEMTModuleMultimediaDao
    abstract fun talentsCompletedDao(): ZEMTTalentsCompletedDao
    abstract fun surveyConfirmAnswerSavedDao(): ZEMTSurveyConfirmAnswerSavedDao
    abstract fun surveyApplyDao(): ZEMTSurveyApplyDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(buildString {
                    append("CREATE TABLE IF NOT EXISTS `SurveyConfirmAnswerSaved` ")
                    append("(`id` INTEGER NOT NULL, ")
                    append("`answerId` INTEGER NOT NULL, `answerOrder` INTEGER NOT NULL, ")
                    append("`questionId` INTEGER NOT NULL, `questionOrder` INTEGER NOT NULL, ")
                    append("`talentId` INTEGER NOT NULL, `talentOrder` INTEGER NOT NULL, ")
                    append("`date` TEXT NOT NULL, ")
                    append("`latitude` TEXT NOT NULL, `longitude` TEXT NOT NULL, ")
                    append("PRIMARY KEY(`id`))")
                })
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `SurveyApplyAnswerSaved` " +
                        "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " `answerId` INTEGER NOT NULL," +
                        " `questionId` INTEGER NOT NULL," +
                        " `status` TEXT NOT NULL," +
                        " `latitude` TEXT NOT NULL," +
                        " `longitude` TEXT NOT NULL," +
                        " `date` TEXT NOT NULL)")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE SurveyDiscoverBreak ADD COLUMN text TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE SurveyDiscoverBreak ADD COLUMN seen INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}