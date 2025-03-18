package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.repository.fake.ZEMTFakeSurveyDiscoverRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeAnswerSavedDiscoverRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeUserRepository
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerSavedMother
import com.upax.zemytalents.domain.repositories.ZEMTAnswersSynchronizer
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

internal class ZEMTSynchronizeAnswersDiscoverUseCaseTest {

    @get:Rule
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    private val collaboratorId = "12345"
    private val userRepository = ZEMTFakeUserRepository(_collaboratorId = collaboratorId)
    private val surveyId = ZEMTSurveyId("54321")
    private val discoverSurveyRepository = ZEMTFakeSurveyDiscoverRepository(surveyId = surveyId)

    @Test
    fun `use case should not synchronize answers with value 0`() = runTest {
        val answersInRepository = listOf(
            ZEMTAnswerSavedMother.apply(value = 1),
            ZEMTAnswerSavedMother.apply(value = 5),
            ZEMTAnswerSavedMother.apply(value = 0),
            ZEMTAnswerSavedMother.apply(value = 1)
        )
        val surveyAnswerSynchronizer = mockk<ZEMTAnswersSynchronizer>()
        coEvery { surveyAnswerSynchronizer.sync(any(), any(), any()) } returns ZEMTResult.Success(
            Unit
        )
        val useCase = ZEMTSynchronizeAnswersDiscoverUseCase(
            answerSavedRepository = ZEMTFakeAnswerSavedDiscoverRepository(
                answersToReturn = answersInRepository
            ),
            answerSynchronizer = surveyAnswerSynchronizer,
            userRepository = userRepository,
            surveyDiscoverRepository = discoverSurveyRepository
        )

        useCase.invoke()

        val answersToSynchronize = listOf(
            answersInRepository[0], answersInRepository[1], answersInRepository[3]
        )
        coVerify { surveyAnswerSynchronizer.sync(any(), any(), answersToSynchronize) }
    }


    @Test
    fun `use case should pass collaborator id from repository`() = runTest {
        val surveyAnswerSynchronizer = mockk<ZEMTAnswersSynchronizer>(relaxed = true)
        val useCase = ZEMTSynchronizeAnswersDiscoverUseCase(
            answerSavedRepository = ZEMTFakeAnswerSavedDiscoverRepository(),
            answerSynchronizer = surveyAnswerSynchronizer,
            userRepository = userRepository,
            surveyDiscoverRepository = discoverSurveyRepository
        )

        useCase.invoke()

        coVerify { surveyAnswerSynchronizer.sync(collaboratorId, any(), any()) }
    }

    @Test
    fun `use case should pass survey id from repository`() = runTest {
        val surveyAnswerSynchronizer = mockk<ZEMTAnswersSynchronizer>(relaxed = true)
        val useCase = ZEMTSynchronizeAnswersDiscoverUseCase(
            answerSavedRepository = ZEMTFakeAnswerSavedDiscoverRepository(),
            answerSynchronizer = surveyAnswerSynchronizer,
            userRepository = userRepository,
            surveyDiscoverRepository = discoverSurveyRepository
        )

        useCase.invoke()

        coVerify { surveyAnswerSynchronizer.sync(any(), surveyId, any()) }
    }


    @Test
    fun `when answer synchronizer returns success then answers should be deleted`() = runTest {
        val surveyAnswerSynchronizer = mockk<ZEMTAnswersSynchronizer>()
        coEvery { surveyAnswerSynchronizer.sync(any(), any(), any()) } returns ZEMTResult.Success(
            Unit
        )
        val answersRepository = ZEMTFakeAnswerSavedDiscoverRepository()
        val useCase = ZEMTSynchronizeAnswersDiscoverUseCase(
            answerSavedRepository = answersRepository,
            answerSynchronizer = surveyAnswerSynchronizer,
            userRepository = userRepository,
            surveyDiscoverRepository = discoverSurveyRepository
        )

        useCase.invoke()

        assertTrue(answersRepository.answersDeleted)
    }

    @Test
    fun `when answer synchronizer returns error then answers should not be deleted`() = runTest {
        val surveyAnswerSynchronizer = mockk<ZEMTAnswersSynchronizer>()
        coEvery { surveyAnswerSynchronizer.sync(any(), any(), any()) } returns ZEMTResult.Error(
            ZEMTDataError.NetworkError("")
        )
        val answersRepository = ZEMTFakeAnswerSavedDiscoverRepository()
        val useCase = ZEMTSynchronizeAnswersDiscoverUseCase(
            answerSavedRepository = answersRepository,
            answerSynchronizer = surveyAnswerSynchronizer,
            userRepository = userRepository,
            surveyDiscoverRepository = discoverSurveyRepository
        )

        useCase.invoke()

        assertFalse(answersRepository.answersDeleted)
    }

}