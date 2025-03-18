package com.upax.zemytalents.rules

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.upax.zemytalents.data.local.database.ZEMTPopulateSurveyDatabaseUseCase
import com.upax.zemytalents.data.local.database.ZEMTTalentsDatabase
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import org.junit.rules.TestWatcher
import org.junit.runner.Description

internal class ZEMTMyTalentsDatabaseTestRule : TestWatcher() {

    private var database: ZEMTTalentsDatabase? = null

    override fun starting(description: Description) {
        super.starting(description)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ZEMTTalentsDatabase::class.java)
            .setTransactionExecutor(Dispatchers.Main.asExecutor())
            .allowMainThreadQueries()
            .build()
    }

    suspend fun populateDatabase(
        surveyId: Int = ZEMTRandomValuesUtil.getInt(),
        initialGroupQuestionsIndex: Int = ZEMTRandomValuesUtil.getInt(),
        totalQuestions: Int = ZEMTRandomValuesUtil.getInt(),
    ) {
        ZEMTPopulateSurveyDatabaseUseCase(
            database!!,
            surveyId = surveyId,
            initialGroupQuestionsIndex = initialGroupQuestionsIndex,
            totalQuestions = totalQuestions
        ).populateDatabase()
    }

    override fun finished(description: Description) {
        super.finished(description)
        database?.clearAllTables()
        database?.close()
        database = null
    }

    fun surveyDao() = database!!.surveyDiscoverDao()
    fun questionDao() = database!!.questionDiscoverDao()
    fun answerOptionDao() = database!!.answerOptionDiscoverDao()
    fun answerSavedDao() = database!!.answerSavedDiscoverDao()
    fun breakDao() = database!!.breakDiscoverDao()
    fun moduleDao() = database!!.moduleDao()

}