package com.upax.zemytalents.rules

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.upax.zemytalents.data.local.database.ZEMTTalentsDatabase
import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleEntity
import com.upax.zemytalents.di.ZEMTDataProvider
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import kotlinx.coroutines.runBlocking
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class SurveyApplyDatabaseRule: TestWatcher() {

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
                surveyId = SURVEY_APPLY_ID,
                name = "Apply",
                order = 1,
                description = "",
                isComplete = false,
                stage = ZEMTModuleStage.APPLY.name
            )
        )
        database.moduleDao().getModuleByStage(ZEMTModuleStage.APPLY.name)
    }

    companion object {
        const val SURVEY_APPLY_ID = "3"
    }

}