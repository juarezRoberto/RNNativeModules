package com.upax.zemytalents.ui.modules.apply.survey

import app.cash.turbine.test
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.ZEMTTalentsMother
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentMother
import com.upax.zemytalents.domain.usecases.ZEMTGetMyUserTalentsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyApplyUseCase
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeApplyAnswersUseCase
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.ui.modules.apply.survey.ZEMTSurveyApplyViewModelMother.createCommonViewModelInstance
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel.Status
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import com.upax.zemytalents.utils.ZHCFlowUtils.avoidFirstUiStateEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ZEMTSurveyApplyViewModelTest {

    @get:Rule
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @Test
    fun `at the beginning isLoadingState should be false`() {
        val viewModel = ZEMTSurveyApplyViewModelMother.random()
        assertFalse(viewModel.isLoadingState.value)
    }

    @Test
    fun `at the beginning surveyFinishedState should be false`() {
        val viewModel = ZEMTSurveyApplyViewModelMother.random()
        assertFalse(viewModel.surveyFinishedState.value)
    }

    @Test
    fun `at the beginning alertsState should be null`() {
        val viewModel = ZEMTSurveyApplyViewModelMother.random()
        assertNull(viewModel.alertsState.value)
    }

    @Test
    fun `at the beginning uiState should contain empty list`() {
        val viewModel = ZEMTSurveyApplyViewModelMother.random()
        assertEquals(
            ZEMTSurveyApplyUiState(dominantTalents = emptyList(), surveyTalents = emptyList()),
            viewModel.uiState.value
        )
    }

    @Test
    fun `when onStart is called then isLoadingState should be updated`() = runTest {
        val viewModel = ZEMTSurveyApplyViewModelMother.apply(
            getSurveyApplyUseCase = mockk<ZEMTGetSurveyApplyUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Error(mockk())
            },
            getUserTalentsUseCase = mockk<ZEMTGetMyUserTalentsUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Error(mockk())
            }
        )
        viewModel.isLoadingState.avoidFirstUiStateEvent().test {
            viewModel.onStart()
            assertTrue(awaitItem())
            assertFalse(awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when getUserTalentsUseCase returns success then update dominantTalents`() = runTest {
        val userTalents = ZEMTTalentsMother.random()
        val viewModel = ZEMTSurveyApplyViewModelMother.apply(
            getSurveyApplyUseCase = mockk<ZEMTGetSurveyApplyUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Success(emptyList())
            },
            getUserTalentsUseCase = mockk<ZEMTGetMyUserTalentsUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Success(userTalents)
            }
        )

        viewModel.uiState.avoidFirstUiStateEvent().test {
            viewModel.onStart()
            assertEquals(userTalents.dominantTalents, awaitItem().dominantTalents)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when getSurveyApplyUseCase returns success then update dominantTalents`() = runTest {
        val surveyTalents = ZEMTRandomValuesUtil.getRandomIntRange()
            .map { ZEMTSurveyTalentMother.random() }

        val viewModel = ZEMTSurveyApplyViewModelMother.apply(
            getSurveyApplyUseCase = mockk<ZEMTGetSurveyApplyUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Success(surveyTalents)
            },
            getUserTalentsUseCase = mockk<ZEMTGetMyUserTalentsUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Success(ZEMTTalentsMother.random())
            }
        )

        val surveyTalentsIds = surveyTalents.map { it.id }
        viewModel.uiState.avoidFirstUiStateEvent().test {
            viewModel.onStart()
            assertEquals(surveyTalentsIds, awaitItem().surveyTalents.map { it.id })
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when getSurveyApplyUseCase returns error then update alertsState`() = runTest {
        val viewModel = ZEMTSurveyApplyViewModelMother.apply(
            getSurveyApplyUseCase = mockk<ZEMTGetSurveyApplyUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Error(mockk())
            },
            getUserTalentsUseCase = mockk<ZEMTGetMyUserTalentsUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Success(ZEMTTalentsMother.random())
            }
        )

        viewModel.alertsState.avoidFirstUiStateEvent().test {
            viewModel.onStart()
            assertEquals(ZEMTSurveyApplyAlerts.SurveyDownloadedError, awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when getUserTalentsUseCase returns error then update alertsState`() = runTest {
        val viewModel = ZEMTSurveyApplyViewModelMother.apply(
            getSurveyApplyUseCase = mockk<ZEMTGetSurveyApplyUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Success(emptyList())
            },
            getUserTalentsUseCase = mockk<ZEMTGetMyUserTalentsUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Error(mockk())
            }
        )

        viewModel.alertsState.avoidFirstUiStateEvent().test {
            viewModel.onStart()
            assertEquals(ZEMTSurveyApplyAlerts.SurveyDownloadedError, awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `onAnswerStatusChange should update answer status`() = runTest {
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(ZEMTSurveyTalentMother.withOneQuestion(id = 1))
        ).also { it.onStart(); advanceUntilIdle() }

        fun getFirstAnswer() = viewModel.uiState.value.surveyTalents.first()
            .question.answerOptions.first()

        val answer = getFirstAnswer().copy(status = Status.UNCHECKED)
        viewModel.onAnswerStatusChange(answer, status = Status.CHECK_POSITIVE)
        advanceUntilIdle()

        assertEquals(Status.CHECK_POSITIVE, getFirstAnswer().status)
    }

    @Test
    fun `onAnswerStatusChange should update alertState when all answers question checked`() =
        runTest {
            val talentName = "Perfectionist"
            val talents = listOf(
                ZEMTSurveyTalentMother.withOneQuestion(id = 1).copy(name = talentName),
                ZEMTSurveyTalentMother.withOneQuestion(id = 2),
                ZEMTSurveyTalentMother.withOneQuestion(id = 3)
            )
            val viewModel = createCommonViewModelInstance(surveyTalents = talents).also {
                it.onStart(); advanceUntilIdle()
            }

            viewModel.uiState.value.surveyTalents.first().let { talent ->
                talent.question.answerOptions.forEach { answer ->
                    viewModel.onAnswerStatusChange(answer, status = Status.CHECK_POSITIVE)
                    advanceUntilIdle()
                }
            }

            val alertExpected = ZEMTSurveyApplyAlerts
                .TalentApplied(talent = talentName, talents.count() - 1)
            assertEquals(alertExpected, viewModel.alertsState.value)
        }


    @Test
    fun `onNextTalent should not update alertState when there is pending talents to answer`() = runTest {
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(
                ZEMTSurveyTalentMother.withOneQuestion(id = 1),
                ZEMTSurveyTalentMother.withOneQuestion(id = 2)
            )
        ).also { it.onStart(); advanceUntilIdle() }

        viewModel.uiState.value.surveyTalents.first().let { talent ->
            talent.question.answerOptions.forEach { answer ->
                viewModel.onAnswerStatusChange(answer, status = Status.CHECK_POSITIVE)
            }
        }

        viewModel.onNextTalent()
        assertNull(viewModel.alertsState.value)
    }

    @Test
    fun `onNextTalent should update alertsState when there is no pending talents`() = runTest {
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(
                ZEMTSurveyTalentMother.withOneQuestion(id = 1),
                ZEMTSurveyTalentMother.withOneQuestion(id = 2),
                ZEMTSurveyTalentMother.withOneQuestion(id = 3)
            )
        ).also { it.onStart(); advanceUntilIdle() }

        viewModel.uiState.value.surveyTalents.forEach { talent ->
            talent.question.answerOptions.forEach { answer ->
                viewModel.onAnswerStatusChange(answer, status = Status.CHECK_POSITIVE)
            }
            viewModel.onNextTalent()
        }

        assertEquals(ZEMTSurveyApplyAlerts.SurveyFinished, viewModel.alertsState.value)
    }

    @Test
    fun `onNextTalent should update current talent`() = runTest {
        val talentName = "Perfectionist"
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(
                ZEMTSurveyTalentMother.apply(id = 1),
                ZEMTSurveyTalentMother.apply(id = 2).copy(name = talentName),
                ZEMTSurveyTalentMother.apply(id = 3)
            )
        ).also { it.onStart(); advanceUntilIdle() }

        viewModel.uiState.value.surveyTalents.first().let { talent ->
            talent.question.answerOptions.forEach { answer ->
                viewModel.onAnswerStatusChange(answer, status = Status.CHECK_POSITIVE)
            }
        }
        viewModel.onNextTalent()

        assertEquals(talentName, viewModel.uiState.value.currentTalent!!.name)
    }

    @Test
    fun `onSurveyFinished should update loading state correctly`() = runTest {
        val synchronizeAnswersUseCase = mockk<ZEMTSynchronizeApplyAnswersUseCase> {
            coEvery { this@mockk.invoke() } returns ZEMTResult.Success(Unit)
        }
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(ZEMTSurveyTalentMother.withOneQuestion(id = 1)),
            synchronizeAnswersUseCase = synchronizeAnswersUseCase
        ).also { it.onStart(); advanceUntilIdle() }

        viewModel.isLoadingState.avoidFirstUiStateEvent().test {
            viewModel.onSurveyFinished()
            assertTrue(awaitItem())
            assertFalse(awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `onSurveyFinished should update alertsState when returns error`() = runTest {
        val synchronizeAnswersUseCase = mockk<ZEMTSynchronizeApplyAnswersUseCase> {
            coEvery { this@mockk.invoke() } returns ZEMTResult.Error(
                ZEMTDataError.ServerError("")
            )
        }
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(ZEMTSurveyTalentMother.withOneQuestion(id = 1)),
            synchronizeAnswersUseCase = synchronizeAnswersUseCase
        ).also { it.onStart(); advanceUntilIdle() }

        viewModel.alertsState.avoidFirstUiStateEvent().test {
            viewModel.onSurveyFinished()
            assertEquals(ZEMTSurveyApplyAlerts.SurveyAnswerSyncError, awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `onSurveyFinished should update surveyFinishedState when returns success`() = runTest {
        val synchronizeAnswersUseCase = mockk<ZEMTSynchronizeApplyAnswersUseCase> {
            coEvery { this@mockk.invoke() } returns ZEMTResult.Success(Unit)
        }
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(ZEMTSurveyTalentMother.withOneQuestion(id = 1)),
            synchronizeAnswersUseCase = synchronizeAnswersUseCase
        ).also { it.onStart(); advanceUntilIdle() }

        viewModel.surveyFinishedState.avoidFirstUiStateEvent().test {
            viewModel.onSurveyFinished()
            assertTrue(awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `onFirstLaunch should call location updater`() = runTest {
        val locationUpdater = mockk<ZEMTLocationUpdater>(relaxed = true)
        val viewModel = ZEMTSurveyApplyViewModelMother.apply(
            locationUpdater = locationUpdater
        )
        viewModel.onFirstLaunch()
        advanceUntilIdle()
        coVerify(exactly = 1) { locationUpdater.updateLocation() }
    }

    @Test
    fun `when onStart is called and user has progress then update alert state`() = runTest {
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(
                ZEMTSurveyTalentMother.withOneQuestionWithOneAnswerChecked(id = 1),
                ZEMTSurveyTalentMother.withOneQuestionWithAllAnswersUnchecked(id = 2),
                ZEMTSurveyTalentMother.withOneQuestionWithAllAnswersUnchecked(id = 3)
            )
        ).also { it.onStart(); advanceUntilIdle() }

        assertEquals(ZEMTSurveyApplyAlerts.SurveyInProgress, viewModel.alertsState.value)
    }

    @Test
    fun `when onStart is called and user does not have progress then update alert state`() = runTest {
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(
                ZEMTSurveyTalentMother.withOneQuestionWithAllAnswersUnchecked(id = 1),
                ZEMTSurveyTalentMother.withOneQuestionWithAllAnswersUnchecked(id = 2),
                ZEMTSurveyTalentMother.withOneQuestionWithAllAnswersUnchecked(id = 3)
            )
        ).also { it.onStart(); advanceUntilIdle() }

        assertNull(viewModel.alertsState.value)
    }

    @Test
    fun `when onStart is called and user has finished the survey then update alert state`() = runTest {
        val viewModel = createCommonViewModelInstance(
            surveyTalents = listOf(
                ZEMTSurveyTalentMother.withOneQuestionWithAllAnswersChecked(id = 1),
                ZEMTSurveyTalentMother.withOneQuestionWithAllAnswersChecked(id = 2),
                ZEMTSurveyTalentMother.withOneQuestionWithAllAnswersChecked(id = 3)
            )
        ).also { it.onStart(); advanceUntilIdle() }

        assertEquals(ZEMTSurveyApplyAlerts.SurveyFinished, viewModel.alertsState.value)
    }

}