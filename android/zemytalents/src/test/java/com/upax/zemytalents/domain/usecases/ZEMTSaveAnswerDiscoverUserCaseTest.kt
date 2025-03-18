package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.repository.fake.ZEMTFakeAnswerOptionDiscoverRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeAnswerSavedDiscoverRepository
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTSaveAnswerError
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionMother
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class ZEMTSaveAnswerDiscoverUserCaseTest {

    @get:Rule
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @Test
    fun `use case should return success when answer is saved`() = runTest {
        val updateGroupQuestionIndexUseCase =
            mockk<ZEMTUpdateGroupQuestionsIndexDiscoverUseCase>(relaxed = true)
        val useCase = ZEMTSaveAnswerDiscoverUserCase(
            answerOptionRepository = ZEMTFakeAnswerOptionDiscoverRepository(),
            answerSavedRepository = ZEMTFakeAnswerSavedDiscoverRepository(totalNeutralAnswer = 10),
            updateGroupQuestionIndexUseCase = updateGroupQuestionIndexUseCase
        )

        val result = useCase.invoke(ZEMTAnswerId(1))

        assertEquals(ZEMTResult.Success(Unit), result)
    }

    @Test
    fun `use case should call repository saveAnswer when returns success`() = runTest {
        val updateGroupQuestionIndexUseCase =
            mockk<ZEMTUpdateGroupQuestionsIndexDiscoverUseCase>(relaxed = true)
        val answer = ZEMTAnswerOptionMother.random()
        val repository = spyk(
            ZEMTFakeAnswerSavedDiscoverRepository(totalNeutralAnswer = 10)
        )
        val useCase = ZEMTSaveAnswerDiscoverUserCase(
            answerOptionRepository = ZEMTFakeAnswerOptionDiscoverRepository(
                answerToReturn = answer,
            ),
            answerSavedRepository = repository,
            updateGroupQuestionIndexUseCase = updateGroupQuestionIndexUseCase
        )

        useCase.invoke(answer.id)

        coVerify(exactly = 1) { repository.saveAnswer(answer) }
    }

    @Test
    fun `use case should call updateGroupQuestionIndexUseCase when returns success`() = runTest {
        val updateGroupQuestionIndexUseCase =
            mockk<ZEMTUpdateGroupQuestionsIndexDiscoverUseCase>(relaxed = true)
        val useCase = ZEMTSaveAnswerDiscoverUserCase(
            answerOptionRepository = ZEMTFakeAnswerOptionDiscoverRepository(),
            answerSavedRepository = ZEMTFakeAnswerSavedDiscoverRepository(totalNeutralAnswer = 10),
            updateGroupQuestionIndexUseCase = updateGroupQuestionIndexUseCase
        )

        useCase.invoke(ZEMTAnswerId(1))

        coVerify(exactly = 1) { updateGroupQuestionIndexUseCase.invoke() }
    }

    @Test
    fun `use case should call first repository saveAnswer and then updateGroupQuestionIndexUseCase when returns success`() =
        runTest {
            val updateGroupQuestionIndexUseCase =
                mockk<ZEMTUpdateGroupQuestionsIndexDiscoverUseCase>(relaxed = true)
            val answer = ZEMTAnswerOptionMother.random()
            val repository = spyk(
                ZEMTFakeAnswerSavedDiscoverRepository(
                    totalNeutralAnswer = 10
                )
            )
            val useCase = ZEMTSaveAnswerDiscoverUserCase(
                answerOptionRepository = ZEMTFakeAnswerOptionDiscoverRepository(
                    answerToReturn = answer
                ),
                answerSavedRepository = repository,
                updateGroupQuestionIndexUseCase = updateGroupQuestionIndexUseCase
            )

            useCase.invoke(answer.id)

            coVerifyOrder {
                repository.saveAnswer(answer)
                updateGroupQuestionIndexUseCase.invoke()
            }
        }

    @Test
    fun `when answer is neutral and total neutral answer is less than 30 then return success`() =
        runTest {
            val updateGroupQuestionIndexUseCase =
                mockk<ZEMTUpdateGroupQuestionsIndexDiscoverUseCase>(relaxed = true)

            val answer = ZEMTAnswerOptionMother.apply(value = 0)
            val useCase = ZEMTSaveAnswerDiscoverUserCase(
                answerOptionRepository = ZEMTFakeAnswerOptionDiscoverRepository(
                    answerToReturn = answer
                ),
                answerSavedRepository = ZEMTFakeAnswerSavedDiscoverRepository(
                    totalNeutralAnswer = 29
                ),
                updateGroupQuestionIndexUseCase = updateGroupQuestionIndexUseCase
            )

            val result = useCase.invoke(answer.id)

            assertEquals(ZEMTResult.Success(Unit), result)
        }

    @Test
    fun `when answer is neutral and total neutral answer is more or equals than 30 then return error`() =
        runTest {
            val updateGroupQuestionIndexUseCase =
                mockk<ZEMTUpdateGroupQuestionsIndexDiscoverUseCase>(relaxed = true)

            val answer = ZEMTAnswerOptionMother.apply(value = 0)
            val useCase = ZEMTSaveAnswerDiscoverUserCase(
                answerOptionRepository = ZEMTFakeAnswerOptionDiscoverRepository(
                    answerToReturn = answer
                ),
                answerSavedRepository = ZEMTFakeAnswerSavedDiscoverRepository(
                    totalNeutralAnswer = 30
                ),
                updateGroupQuestionIndexUseCase = updateGroupQuestionIndexUseCase
            )

            val result = useCase.invoke(answer.id)

            assertEquals(
                ZEMTResult.Error(ZEMTSaveAnswerError.MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED),
                result
            )
        }

}