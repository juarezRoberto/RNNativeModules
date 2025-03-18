package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.repository.fake.ZEMTDummyTalentsCompletedRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeAnswersSynchronizer
import com.upax.zemytalents.data.repository.fake.ZEMTFakeSurveyApplyAnswersRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeSurveyApplyRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeUserRepository
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerSavedMother
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus.CHECK_NEGATIVE
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus.CHECK_POSITIVE
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

internal class ZEMTSynchronizeApplyAnswersUseCaseTest {

    @get:Rule
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    private val userRepository = ZEMTFakeUserRepository()
    private val surveyApplyRepository = ZEMTFakeSurveyApplyRepository()
    private val answerSynchronizer = ZEMTFakeAnswersSynchronizer()
    private val answersRepository = ZEMTFakeSurveyApplyAnswersRepository()
    private val talentsRepository = ZEMTDummyTalentsCompletedRepository()
    private val useCase = ZEMTSynchronizeApplyAnswersUseCase(
        userRepository, surveyApplyRepository, answerSynchronizer, answersRepository, talentsRepository
    )

    @Test
    fun `use case should synchronizer answers with status CHECK_POSITIVE`() = runTest {
        val answersInRepository = listOf(
            ZEMTSurveyApplyAnswerSavedMother.apply(id = 1, status = CHECK_POSITIVE),
            ZEMTSurveyApplyAnswerSavedMother.apply(id = 2, status = CHECK_NEGATIVE),
            ZEMTSurveyApplyAnswerSavedMother.apply(id = 3, status = CHECK_NEGATIVE),
            ZEMTSurveyApplyAnswerSavedMother.apply(id = 4, status = CHECK_POSITIVE),
            ZEMTSurveyApplyAnswerSavedMother.apply(id = 5, status = CHECK_POSITIVE),
            ZEMTSurveyApplyAnswerSavedMother.apply(id = 5, status = CHECK_NEGATIVE),
        ).onEach { answer -> answersRepository.saveAnswer(answer) }

        useCase.invoke()

        val answersIdExpected = answersInRepository
            .filter { it.status == CHECK_POSITIVE }.map { it.id }
        val answersId = answerSynchronizer.answersSynchronized.map { it.id.value }
        assertEquals(answersIdExpected, answersId)
    }

    @Test
    fun `when synchronizer returns success then finish survey`() = runTest {
        useCase.invoke()
        assertTrue(surveyApplyRepository.finished)
    }

    @Test
    fun `when synchronizer returns error then survey should not be finished`() = runTest {
        answerSynchronizer.error = ZEMTDataError.NetworkError("Internal server error")
        useCase.invoke()
        assertFalse(surveyApplyRepository.finished)
    }

    @Test
    fun `when synchronizer returns success then call complete talents method`() = runTest {
        useCase.invoke()
        assertTrue(talentsRepository.completeTalentsCalled)
    }

    @Test
    fun `complete talents method is called with collaborator id from repository`() = runTest {
        useCase.invoke()
        assertEquals(userRepository.collaboratorId, talentsRepository.collaboratorIdCalled)
    }

}