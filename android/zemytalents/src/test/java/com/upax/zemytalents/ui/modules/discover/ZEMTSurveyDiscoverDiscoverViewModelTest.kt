package com.upax.zemytalents.ui.modules.discover

import app.cash.turbine.test
import com.upax.zemytalents.data.repository.fake.ZEMTFakeSurveyDownloader
import com.upax.zemytalents.data.repository.fake.ZEMTFakeGroupQuestionsDiscoverRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeSurveyDiscoverBreaksRepository
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTSaveAnswerError
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionMother
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakDiscoverMother
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverReminder
import com.upax.zemytalents.domain.usecases.ZEMTSaveAnswerDiscoverUserCase
import com.upax.zemytalents.domain.usecases.ZEMTSkipGroupQuestionsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeAnswersDiscoverUseCase
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.ui.modules.discover.model.ZEMTDiscoverAnswerUiModelMother
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverAlerts
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverUiState
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import com.upax.zemytalents.utils.ZHCFlowUtils.avoidFirstUiStateEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ZEMTSurveyDiscoverDiscoverViewModelTest {

    @get:Rule
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @Test
    fun `timeInSecondsToResponseQuestions should be 35`() {
        val viewModel = ZEMTDiscoverViewModelMother.random()
        assertEquals(35, viewModel.uiState.value.timeInSecondsToResponseQuestions)
    }

    @Test
    fun `at the beginning ui state has null values`() {
        val viewModel = ZEMTDiscoverViewModelMother.random()
        assertEquals(
            ZEMTSurveyDiscoverUiState(null, null, null),
            viewModel.uiState.value
        )
    }

    @Test
    fun `ui state should return totalGroupQuestions repositories correctly`() = runTest {
        val totalGroupQuestionsToReturn = ZEMTRandomValuesUtil.getInt()
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            surveyGroupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                totalGroupQuestions = totalGroupQuestionsToReturn
            )
        )

        viewModel.uiState.avoidFirstUiStateEvent().test {
            assertEquals(totalGroupQuestionsToReturn, awaitItem().totalGroupQuestions)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `ui state should return totalGroupQuestionsAnswered from repository correctly`() = runTest {
        val totalGroupQuestionsAnswered = ZEMTRandomValuesUtil.getInt()
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            surveyGroupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                _totalGroupQuestionsAnswered = totalGroupQuestionsAnswered
            )
        )

        viewModel.uiState.avoidFirstUiStateEvent().test {
            assertEquals(totalGroupQuestionsAnswered, awaitItem().totalGroupQuestionsAnswered)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `ui state should return currentGroupQuestions from repository correctly`() = runTest {
        val leftQuestion = ZEMTQuestionMother.random()
        val rightQuestion = ZEMTQuestionMother.random()
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            surveyGroupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                _currentGroupQuestions = ZEMTGroupQuestionsDiscover(
                    ZEMTGroupQuestionIndexDiscover(0), leftQuestion, rightQuestion
                )
            )
        )

        viewModel.uiState.avoidFirstUiStateEvent().test {
            val currentGroupQuestions = awaitItem().currentGroupQuestions!!
            assertEquals(leftQuestion.id, currentGroupQuestions.leftQuestion.id)
            assertEquals(rightQuestion.id, currentGroupQuestions.rightQuestion.id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `view model should download the survey if it is not downloaded`() = runTest {
        val surveyDownloader = spyk(ZEMTFakeSurveyDownloader(needToDownload = true))
        val viewModel = ZEMTDiscoverViewModelMother.apply(surveyDownloader = surveyDownloader)

        viewModel.uiState.avoidFirstUiStateEvent().test {
            awaitItem()
            coVerify(exactly = 1) { surveyDownloader.download() }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `view model should not download the survey if it is downloaded`() = runTest {
        val surveyDownloader = spyk(ZEMTFakeSurveyDownloader(needToDownload = false))
        val viewModel = ZEMTDiscoverViewModelMother.apply(surveyDownloader = surveyDownloader)

        viewModel.uiState.avoidFirstUiStateEvent().test {
            awaitItem()
            coVerify(exactly = 0) { surveyDownloader.download() }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when survey downloader returns error then update state correctly`() = runTest {
        val surveyDownloader = ZEMTFakeSurveyDownloader(
            needToDownload = true,
            errorToReturn = ZEMTDataError.ServerError("500")
        )
        val viewModel = ZEMTDiscoverViewModelMother.apply(surveyDownloader = surveyDownloader)

        viewModel.uiState.avoidFirstUiStateEvent().test {
            awaitItem()
            assertEquals(ZEMTSurveyDiscoverAlerts.SurveyDownloadError, viewModel.alertsState.value)
        }
    }

    @Test
    fun `retrySurveyDownload should update loader state when need to download`() = runTest {
        val surveyDownloader = ZEMTFakeSurveyDownloader(needToDownload = true)
        val viewModel = ZEMTDiscoverViewModelMother.apply(surveyDownloader = surveyDownloader)

        viewModel.isLoadingState.test {
            assertFalse(awaitItem())
            viewModel.onRetrySurveyDownload()
            assertTrue(awaitItem())
            assertFalse(awaitItem())
        }
    }

    @Test
    fun `restartShowErrorState should set false to state`() = runTest {
        val surveyDownloader = ZEMTFakeSurveyDownloader(
            needToDownload = true,
            errorToReturn = ZEMTDataError.ServerError("500")
        )
        val viewModel = ZEMTDiscoverViewModelMother.apply(surveyDownloader = surveyDownloader)

        viewModel.uiState.avoidFirstUiStateEvent().test {
            awaitItem()
            assertEquals(ZEMTSurveyDiscoverAlerts.SurveyDownloadError, viewModel.alertsState.value)
            viewModel.onRestartAlertState()
            assertEquals(null, viewModel.alertsState.value)
        }
    }

    @Test
    fun `when onSaveAnswer is called and return MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED then update uiState correctly`() =
        runTest {
            val saveAnswerUseCase = mockk<ZEMTSaveAnswerDiscoverUserCase>()
            coEvery {
                saveAnswerUseCase.invoke(any())
            } returns ZEMTResult.Error(ZEMTSaveAnswerError.MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED)


            val viewModel = ZEMTDiscoverViewModelMother.apply(saveAnswerUseCase = saveAnswerUseCase)

            viewModel.uiState.avoidFirstUiStateEvent().test {
                assertTrue(awaitItem().currentAnswers!![2].enabled)
                viewModel.onSaveAnswer(ZEMTDiscoverAnswerUiModelMother.random())
                advanceUntilIdle()
                awaitItem() // await of alert state
                assertFalse(awaitItem().currentAnswers!![2].enabled)
            }
        }

    @Test
    fun `when onSaveAnswer is called and return MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED then update _alertsState correctly`() =
        runTest {
            val saveAnswerUseCase = mockk<ZEMTSaveAnswerDiscoverUserCase>()
            coEvery {
                saveAnswerUseCase.invoke(any())
            } returns ZEMTResult.Error(ZEMTSaveAnswerError.MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED)


            val viewModel = ZEMTDiscoverViewModelMother.apply(saveAnswerUseCase = saveAnswerUseCase)

            viewModel.alertsState.avoidFirstUiStateEvent().test {
                viewModel.onSaveAnswer(ZEMTDiscoverAnswerUiModelMother.random())
                advanceUntilIdle()
                assertEquals(ZEMTSurveyDiscoverAlerts.MaxNumberOfNeutralAnswers, awaitItem())
            }
        }

    @Test
    fun `onGroupQuestionsSkip should call skipGroupQuestionsUseCase with neutral answer id`() =
        runTest {
            val answerId = ZEMTAnswerId(1)
            val skipGroupQuestionsUseCase = mockk<ZEMTSkipGroupQuestionsUseCase>()
            coEvery { skipGroupQuestionsUseCase.invoke(answerId) } returns ZEMTResult.Success(Unit)
            val answersLeftQuestion = listOf(
                ZEMTAnswerOptionMother.apply(id = answerId, order = 3, value = 0),
                ZEMTAnswerOptionMother.apply(order = 2, value = 5),
                ZEMTAnswerOptionMother.apply(order = 1, value = 1)
            )
            val viewModel = ZEMTDiscoverViewModelMother.apply(
                skipGroupQuestionsUseCase = skipGroupQuestionsUseCase,
                surveyGroupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                    _currentGroupQuestions = ZEMTGroupQuestionsMother.apply(
                        leftQuestion = ZEMTQuestionMother.apply(answers = answersLeftQuestion)
                    )
                )
            )

            viewModel.uiState.avoidFirstUiStateEvent().test {
                awaitItem()
                viewModel.onGroupQuestionsSkip()
                advanceUntilIdle()
                coVerify { skipGroupQuestionsUseCase.invoke(answerId) }
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when onGroupQuestionsSkip returns error then update isTimerVisibleState to false`() =
        runTest {
            val skipGroupQuestionsUseCase = mockk<ZEMTSkipGroupQuestionsUseCase>()
            coEvery { skipGroupQuestionsUseCase.invoke(any()) } returns ZEMTResult.Error(
                ZEMTSaveAnswerError.MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED
            )
            val viewModel = ZEMTDiscoverViewModelMother.apply(
                skipGroupQuestionsUseCase = skipGroupQuestionsUseCase
            )

            viewModel.uiState.avoidFirstUiStateEvent().test {
                assertTrue(awaitItem().isTimerVisible)
                viewModel.onGroupQuestionsSkip()
                advanceUntilIdle()
                awaitItem() // await of alert state
                assertFalse(awaitItem().isTimerVisible)
            }
        }

    @Test
    fun `when onGroupQuestionsSkip returns error then update alert state correctly`() = runTest {
        val skipGroupQuestionsUseCase = mockk<ZEMTSkipGroupQuestionsUseCase>()
        coEvery { skipGroupQuestionsUseCase.invoke(any()) } returns ZEMTResult.Error(
            ZEMTSaveAnswerError.MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED
        )
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            skipGroupQuestionsUseCase = skipGroupQuestionsUseCase
        )

        viewModel.uiState.avoidFirstUiStateEvent().test {
            awaitItem()
            viewModel.onGroupQuestionsSkip()
            advanceUntilIdle()
            awaitItem()
            assertEquals(
                ZEMTSurveyDiscoverAlerts.MaxNumberOfNeutralAnswers,
                viewModel.alertsState.value
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when onScheduleReminder is called then verify surveyReminder is invoke`() = runTest {
        val surveyReminder = mockk<ZEMTSurveyDiscoverReminder>(relaxed = true)
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            surveyReminder = surveyReminder
        )

        viewModel.onScheduleReminder()

        verify(exactly = 1) { surveyReminder.scheduleReminder() }
    }

    @Test
    fun `when onCancelReminderIfExits is called then verify surveyReminder is invoke`() = runTest {
        val surveyReminder = mockk<ZEMTSurveyDiscoverReminder>(relaxed = true)
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            surveyReminder = surveyReminder
        )

        viewModel.onCancelReminderIfExits()

        verify(exactly = 1) { surveyReminder.cancelPendingReminders() }
    }


    @Test
    fun `onSurveyFinished should update loader state when need to correctly`() = runTest {
        val synchronizeSurveyAnswersUseCase = mockk<ZEMTSynchronizeAnswersDiscoverUseCase>()
        coEvery { synchronizeSurveyAnswersUseCase.invoke() } returns ZEMTResult.Success(Unit)
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            synchronizeSurveyAnswersUseCase = synchronizeSurveyAnswersUseCase
        )
        viewModel.isLoadingState.test {
            assertFalse(awaitItem())
            viewModel.onSurveyFinished()
            assertTrue(awaitItem())
            assertFalse(awaitItem())
        }
    }

    @Test
    fun `when onSurveyFinished returns error then update error state correctly`() = runTest {
        val synchronizeSurveyAnswersUseCase = mockk<ZEMTSynchronizeAnswersDiscoverUseCase>()
        coEvery { synchronizeSurveyAnswersUseCase.invoke() } returns ZEMTResult.Error(
            ZEMTDataError.ServerError(
                "Internal server error"
            )
        )
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            synchronizeSurveyAnswersUseCase = synchronizeSurveyAnswersUseCase
        )

        viewModel.onSurveyFinished()
        advanceUntilIdle()
        assertEquals(
            ZEMTSurveyDiscoverAlerts.SurveyAnswerSynchronizerError,
            viewModel.alertsState.value
        )
    }

    @Test
    fun `when onSurveyFinished returns success then update surveyFinishedState state correctly`() =
        runTest {
            val synchronizeSurveyAnswersUseCase = mockk<ZEMTSynchronizeAnswersDiscoverUseCase>()
            coEvery { synchronizeSurveyAnswersUseCase.invoke() } returns ZEMTResult.Success(Unit)
            val viewModel = ZEMTDiscoverViewModelMother.apply(
                synchronizeSurveyAnswersUseCase = synchronizeSurveyAnswersUseCase
            )

            viewModel.surveyFinishedState.test {
                assertFalse(awaitItem())
                viewModel.onSurveyFinished()
                assertTrue(awaitItem())
            }
        }

    @Test
    fun `when current group question have break should update alert state`() = runTest {
        val indexGroupQuestion = ZEMTGroupQuestionIndexDiscover(5)
        val groupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
            _currentGroupQuestions = ZEMTGroupQuestionsMother.apply(index = indexGroupQuestion)
        )
        val breaksRepository = ZEMTFakeSurveyDiscoverBreaksRepository(
            breaks = listOf(ZEMTBreakDiscoverMother.apply(indexGroupQuestion = indexGroupQuestion))
        )
        val viewModel = ZEMTDiscoverViewModelMother.apply(
            surveyGroupQuestionRepository = groupQuestionRepository,
            breaksRepository = breaksRepository
        )

        viewModel.uiState.avoidFirstUiStateEvent().test {
            awaitItem()
            assertEquals(ZEMTSurveyDiscoverAlerts.TakeBreak, viewModel.alertsState.value)
            awaitItem() // alert state
        }
    }

    @Test
    fun `if currentGroupQuestion have break then breakToReturn should have value and then should be null`() =
        runTest {
            val indexGroupQuestion = ZEMTGroupQuestionIndexDiscover(5)
            val groupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                _currentGroupQuestions = ZEMTGroupQuestionsMother.apply(index = indexGroupQuestion)
            )
            val breakToReturn =
                ZEMTBreakDiscoverMother.apply(indexGroupQuestion = indexGroupQuestion)
            val breaksRepository = ZEMTFakeSurveyDiscoverBreaksRepository(
                breaks = listOf(breakToReturn)
            )
            val viewModel = ZEMTDiscoverViewModelMother.apply(
                surveyGroupQuestionRepository = groupQuestionRepository,
                breaksRepository = breaksRepository
            )

            viewModel.uiState.avoidFirstUiStateEvent().test {
                assertEquals(breakToReturn, awaitItem().nextBreakToShow)
                assertEquals(null, awaitItem().nextBreakToShow)
            }
        }

}