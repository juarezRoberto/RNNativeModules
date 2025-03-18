package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.repository.fake.ZEMTFakeGroupQuestionsDiscoverRepository
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class ZEMTGetSurveyDiscoverProgressUseCaseTest {

    @get:Rule
    val coroutinesTestRule = ZEMTCoroutinesTestRule()


    @Test
    fun `when totalGroupQuestionsAnswered is half of totalGroupQuestions then return fifty percent`() =
        runTest {
            val groupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                totalGroupQuestions = 100,
                _totalGroupQuestionsAnswered = 50
            )
            val useCase = ZEMTGetSurveyDiscoverProgressUseCase(groupQuestionRepository)

            val currentProgress = useCase.invoke().first()

            assertEquals(0.5f, currentProgress)
        }

    @Test
    fun `when totalGroupQuestionsAnswered is equals totalGroupQuestions then return one hundred percent`() =
        runTest {
            val groupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                totalGroupQuestions = 190,
                _totalGroupQuestionsAnswered = 190
            )
            val useCase = ZEMTGetSurveyDiscoverProgressUseCase(groupQuestionRepository)

            val currentProgress = useCase.invoke().first()

            assertEquals(1f, currentProgress)
        }

    @Test
    fun `when totalGroupQuestions is 0 then return one percent`() =
        runTest {
            val groupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                totalGroupQuestions = 0,
                _totalGroupQuestionsAnswered = 1
            )
            val useCase = ZEMTGetSurveyDiscoverProgressUseCase(groupQuestionRepository)

            val currentProgress = useCase.invoke().first()

            assertEquals(0.01f, currentProgress)
        }

    @Test
    fun `when totalGroupQuestionsAnswered is 0 then return one percent`() =
        runTest {
            val groupQuestionRepository = ZEMTFakeGroupQuestionsDiscoverRepository(
                totalGroupQuestions = 1,
                _totalGroupQuestionsAnswered = 0
            )
            val useCase = ZEMTGetSurveyDiscoverProgressUseCase(groupQuestionRepository)

            val currentProgress = useCase.invoke().first()

            assertEquals(0.01f, currentProgress)
        }

}