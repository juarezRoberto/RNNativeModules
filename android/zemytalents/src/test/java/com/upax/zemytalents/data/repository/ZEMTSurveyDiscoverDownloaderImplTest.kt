package com.upax.zemytalents.data.repository

import com.upax.zcservicecoordinator.expose.models.ZCSCBaseResponseV2
import com.upax.zcservicecoordinator.expose.models.ZCSCErrorResponse
import com.upax.zemytalents.data.local.database.entity.ZEMTModuleEntityMother
import com.upax.zemytalents.data.remote.api.ZEMTSurveyDiscoverApiService
import com.upax.zemytalents.data.remote.mapper.ZEMTSurveyDiscoverResponseToModelMapperMother
import com.upax.zemytalents.data.remote.response.ZEMTDiscoverSurveyResponseMother
import com.upax.zemytalents.data.repository.fake.ZEMTFakeUserRepository
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.rules.ZEMTMyTalentsDatabaseTestRule
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ZEMTSurveyDiscoverDownloaderImplTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @get:Rule(order = 1)
    val databaseTestRule = ZEMTMyTalentsDatabaseTestRule()

    private val api = mockk<ZEMTSurveyDiscoverApiService>()
    private val surveyRegistrar = mockk<ZEMTSurveyDiscoverRegistrar>()
    private val collaboratorId = "12345"

    private fun getSurveyDiscoverDownloader(): ZEMTSurveyDiscoverDownloaderImpl {
        return ZEMTSurveyDiscoverDownloaderImpl(
            api = api,
            discoverSurveyResponseToModelMapper = ZEMTSurveyDiscoverResponseToModelMapperMother.random(),
            surveyRegistrar = surveyRegistrar,
            surveyDao = databaseTestRule.surveyDao(),
            moduleDao = databaseTestRule.moduleDao(),
            userRepository = ZEMTFakeUserRepository(_collaboratorId = collaboratorId)
        )
    }

    @After
    fun clearMocks() {
        io.mockk.clearMocks(api)
    }

    @Test
    fun `needToDownload should return true when survey group question index is 0`() = runTest {
        val surveyDiscoverDownloader = getSurveyDiscoverDownloader()
        assertTrue(surveyDiscoverDownloader.needToDownload())
    }

    @Test
    fun `needToDownload should return false when survey group question index is greater than 0`() =
        runTest {
            databaseTestRule.populateDatabase()
            databaseTestRule.surveyDao().updateCurrentGroupQuestionIndex(1)
            val surveyDiscoverDownloader = getSurveyDiscoverDownloader()
            assertFalse(surveyDiscoverDownloader.needToDownload())
        }

    @Test
    fun `when api return error then download return error`() =
        runTest {
            databaseTestRule.moduleDao().insert(
                ZEMTModuleEntityMother.apply(moduleId = 2, surveyId = "99")
            )
            coEvery {
                api.getSurvey(any(), any())
            } returns ZCSCBaseResponseV2(error = ZCSCErrorResponse(code = "500"))
            val surveyDiscoverDownloader = getSurveyDiscoverDownloader()

            val result = surveyDiscoverDownloader.download()

            assertTrue(result is ZEMTResult.Error)
        }

    @Test
    fun `when api return success then download return success`() = runTest {
        databaseTestRule.moduleDao().insert(
            ZEMTModuleEntityMother.apply(moduleId = 2, surveyId = "99")
        )
        coJustRun { surveyRegistrar.invoke(any()) }
        coEvery {
            api.getSurvey(any(), any())
        } returns ZCSCBaseResponseV2(
            data = ZEMTDiscoverSurveyResponseMother.random(),
            httpCode = 200
        )
        val surveyDiscoverDownloader = getSurveyDiscoverDownloader()

        val result = surveyDiscoverDownloader.download()

        assertTrue(result is ZEMTResult.Success)
    }

    @Test
    fun `when api return success then surveyRegistrar should be invoke`() = runTest {
        databaseTestRule.moduleDao().insert(
            ZEMTModuleEntityMother.apply(moduleId = 2, surveyId = "99")
        )
        coJustRun { surveyRegistrar.invoke(any()) }
        coEvery {
            api.getSurvey(any(), any())
        } returns ZCSCBaseResponseV2(
            data = ZEMTDiscoverSurveyResponseMother.random(),
            httpCode = 200
        )
        val surveyDiscoverDownloader = getSurveyDiscoverDownloader()

        surveyDiscoverDownloader.download()

        coVerify(exactly = 1) { surveyRegistrar.invoke(any()) }
    }

    @Test
    fun `api getSurvey should be invoke with survey id from database`() = runTest {
        val surveyId = "99"
        databaseTestRule.moduleDao().insert(
            ZEMTModuleEntityMother.apply(stage = ZEMTModuleStage.DISCOVER.name, surveyId = surveyId)
        )
        coJustRun { surveyRegistrar.invoke(any()) }
        coEvery {
            api.getSurvey(surveyId, any())
        } returns ZCSCBaseResponseV2(
            data = ZEMTDiscoverSurveyResponseMother.random(),
            httpCode = 200
        )
        val surveyDiscoverDownloader = getSurveyDiscoverDownloader()

        surveyDiscoverDownloader.download()

        coVerify(exactly = 1) { api.getSurvey(surveyId, any()) }
    }

    @Test
    fun `api getSurvey should be invoke with collaborator id from repository`() = runTest {
        databaseTestRule.moduleDao().insert(
            ZEMTModuleEntityMother.apply(moduleId = 2, surveyId = "99")
        )
        coJustRun { surveyRegistrar.invoke(any()) }
        coEvery {
            api.getSurvey(any(), collaboratorId)
        } returns ZCSCBaseResponseV2(
            data = ZEMTDiscoverSurveyResponseMother.random(),
            httpCode = 200
        )
        val surveyDiscoverDownloader = getSurveyDiscoverDownloader()

        surveyDiscoverDownloader.download()

        coVerify(exactly = 1) { api.getSurvey(any(), collaboratorId) }
    }

}