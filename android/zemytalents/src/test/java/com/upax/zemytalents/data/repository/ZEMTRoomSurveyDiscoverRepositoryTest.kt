package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTSurveyDiscoverEntity
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.rules.ZEMTMyTalentsDatabaseTestRule
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ZEMTRoomSurveyDiscoverRepositoryTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @get:Rule(order = 1)
    val databaseTestRule = ZEMTMyTalentsDatabaseTestRule()

    @Test
    fun `repository should return survey id from database`() = runTest {
        val surveyId = ZEMTRandomValuesUtil.getInt()
        databaseTestRule.surveyDao().insertDiscoverSurvey(
            ZEMTSurveyDiscoverEntity(surveyId, ZEMTRandomValuesUtil.getInt())
        )
        val repository = ZEMTRoomSurveyDiscoverRepository(databaseTestRule.surveyDao())

        assertEquals(ZEMTSurveyId(surveyId.toString()), repository.getId())
    }

}