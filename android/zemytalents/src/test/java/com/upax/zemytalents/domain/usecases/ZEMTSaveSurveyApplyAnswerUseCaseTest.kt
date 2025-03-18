package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.repository.fake.ZEMTFakeDateRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeLocationRepository
import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerSaved
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ZEMTSaveSurveyApplyAnswerUseCaseTest {

    @get:Rule
    val coroutinesTestRule = ZEMTCoroutinesTestRule()
    private val answerId = 99
    private val questionId = 88
    private val location = ZEMTLocation(
        ZEMTRandomValuesUtil.getString(),
        ZEMTRandomValuesUtil.getString()
    )
    private val date = ZEMTRandomValuesUtil.getString()
    private val status = ZEMTSurveyApplyAnswerStatus.CHECK_POSITIVE
    private val surveyAnswersRepository: ZEMTSurveyApplyAnswersRepository = mockk()
    private val locationRepository = ZEMTFakeLocationRepository().also {
        it.updateLocation(location)
    }
    private val dateRepository = ZEMTFakeDateRepository().also {
        it.setDate(date)
    }
    private val useCase = ZEMTSaveSurveyApplyAnswerUseCase(
        surveyAnswersRepository,
        locationRepository,
        dateRepository
    )

    @Before
    fun setUp() {
        clearMocks(surveyAnswersRepository)
    }

    @Test
    fun `use case should call surveyAnswersRepository with correct answerSaved class`() = runTest {
        val slot = slot<ZEMTSurveyApplyAnswerSaved>()
        coEvery { surveyAnswersRepository.saveAnswer(capture(slot)) } just Runs

        useCase.invoke(answerId, questionId, status)

        val answerSavedExpected = ZEMTSurveyApplyAnswerSaved(
            id = answerId,
            questionId = questionId,
            status = status,
            location = location,
            date = date,
            order = 0
        )
        assertEquals(answerSavedExpected, slot.captured)
    }

    @Test
    fun `use case should call surveyAnswersRepository only once`() = runTest {
        coEvery { surveyAnswersRepository.saveAnswer(any()) } just Runs

        useCase.invoke(answerId, questionId, status)

        coVerify(exactly = 1) { surveyAnswersRepository.saveAnswer(any())  }
    }

}