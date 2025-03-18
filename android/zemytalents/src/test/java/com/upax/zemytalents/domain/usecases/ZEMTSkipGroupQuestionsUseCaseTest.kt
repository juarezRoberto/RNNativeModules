package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.repository.fake.ZEMTFakeGroupQuestionsDiscoverRepository
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

internal class ZEMTSkipGroupQuestionsUseCaseTest {

    @get:Rule
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @Test
    fun `use case should call updateGroupQuestionIndexUseCase when is not the last question`() =
        runTest {
            val saveAnswerUseCase = mockk<ZEMTSaveAnswerDiscoverUserCase>()
            val updateGroupQuestionIndexUseCase = mockk<ZEMTUpdateGroupQuestionsIndexDiscoverUseCase>()
            coJustRun { updateGroupQuestionIndexUseCase.invoke() }

            val useCase = ZEMTSkipGroupQuestionsUseCase(
                updateGroupQuestionIndexUseCase = updateGroupQuestionIndexUseCase,
                saveAnswerUseCase = saveAnswerUseCase,
                groupQuestionsRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                    totalGroupQuestions = 10,
                    _totalGroupQuestionsAnswered = 8
                )
            )

            useCase.invoke(ZEMTAnswerId(1))

            coVerify(exactly = 1) { updateGroupQuestionIndexUseCase.invoke() }
        }

    @Test
    fun `use case should call saveAnswerUseCase when is the last question`() =
        runTest {
            val saveAnswerUseCase = mockk<ZEMTSaveAnswerDiscoverUserCase>()
            val updateGroupQuestionIndexUseCase = mockk<ZEMTUpdateGroupQuestionsIndexDiscoverUseCase>()

            val answerId = ZEMTAnswerId(1)
            coEvery { saveAnswerUseCase.invoke(answerId) } returns ZEMTResult.Success(Unit)

           val useCase = ZEMTSkipGroupQuestionsUseCase(
                updateGroupQuestionIndexUseCase = updateGroupQuestionIndexUseCase,
                saveAnswerUseCase = saveAnswerUseCase,
                groupQuestionsRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                    totalGroupQuestions = 10,
                    _totalGroupQuestionsAnswered = 9
                )
            )

            useCase.invoke(answerId)

            coVerify(exactly = 1) { saveAnswerUseCase.invoke(answerId) }
        }

}