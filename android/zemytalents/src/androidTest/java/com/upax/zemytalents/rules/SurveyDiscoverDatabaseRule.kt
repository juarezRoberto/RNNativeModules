package com.upax.zemytalents.rules

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.upax.zemytalents.data.local.database.ZEMTTalentsDatabase
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTSurveyDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleEntity
import com.upax.zemytalents.di.ZEMTDataProvider
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import kotlinx.coroutines.runBlocking
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class SurveyDiscoverDatabaseRule: TestWatcher() {

    private val context: Context = ApplicationProvider.getApplicationContext()

    override fun starting(description: Description?) = runBlocking {
        populateDatabase(ZEMTDataProvider.provideLocalDatabase(context))
    }

    override fun finished(description: Description?) {
        ZEMTDataProvider.provideLocalDatabase(context).clearAllTables()
    }

    private suspend fun populateDatabase(database: ZEMTTalentsDatabase) {
        database.moduleDao().insert(
            ZEMTModuleEntity(
                moduleId = 1,
                surveyId = SURVEY_DISCOVER_ID,
                name = "Discover",
                order = 1,
                description = "",
                isComplete = false,
                stage = ZEMTModuleStage.DISCOVER.name
            )
        )
        database.surveyDiscoverDao().insertDiscoverSurvey(
            ZEMTSurveyDiscoverEntity(
                surveyId = SURVEY_DISCOVER_ID.toInt(),
                groupQuestionIndex = 0
            )
        )
    }

    companion object {
        const val SURVEY_DISCOVER_ID = "1"
    }

}