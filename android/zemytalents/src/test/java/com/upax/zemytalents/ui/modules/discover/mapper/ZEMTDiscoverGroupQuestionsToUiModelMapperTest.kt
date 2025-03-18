package com.upax.zemytalents.ui.modules.discover.mapper

import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionMother
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.ui.modules.discover.survey.mapper.ZEMTDiscoverGroupQuestionsToUiModelMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ZEMTDiscoverGroupQuestionsToUiModelMapperTest {

    @Test
    fun `mapper should return null when group question is null`() {
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(null, false)

        assertEquals(null, groupQuestionUiModel)
    }

    @Test
    fun `mapper should map left question correctly`() {
        val leftQuestion = ZEMTQuestionMother.random()
        val groupQuestion = ZEMTGroupQuestionsMother.apply(leftQuestion = leftQuestion)
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertEquals(leftQuestion.id, groupQuestionUiModel!!.leftQuestion.id)
        assertEquals(leftQuestion.text, groupQuestionUiModel.leftQuestion.text)
    }

    @Test
    fun `mapper should map right question correctly`() {
        val rightQuestion = ZEMTQuestionMother.random()
        val groupQuestion = ZEMTGroupQuestionsMother.apply(rightQuestion = rightQuestion)
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertEquals(rightQuestion.id, groupQuestionUiModel!!.rightQuestion.id)
        assertEquals(rightQuestion.text, groupQuestionUiModel.rightQuestion.text)
    }

    @Test
    fun `mapper should map only five answers`() {
        val groupQuestion = ZEMTGroupQuestionsMother.random()
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertEquals(5, groupQuestionUiModel!!.answers.count())
    }

    @Test
    fun `mapper should return empty answers when questions have less than 3 answers`() {
        val leftQuestion = ZEMTQuestionMother.apply(
            answers = listOf(ZEMTAnswerOptionMother.random(), ZEMTAnswerOptionMother.random())
        )
        val rightQuestion = ZEMTQuestionMother.apply(
            answers = listOf(ZEMTAnswerOptionMother.random(), ZEMTAnswerOptionMother.random())
        )
        val groupQuestion = ZEMTGroupQuestionsMother.apply(
            leftQuestion = leftQuestion,
            rightQuestion = rightQuestion
        )
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertTrue(groupQuestionUiModel!!.answers.isEmpty())
    }

    @Test
    fun `mapper should map neutral answers icon correctly`() {
        val groupQuestion = ZEMTGroupQuestionsMother.random()
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertEquals(
            R.drawable.zemt_ic_sentiment_neutral_selected,
            groupQuestionUiModel!!.answers[2].iconSelected
        )
        assertEquals(
            R.drawable.zemt_ic_sentiment_neutral,
            groupQuestionUiModel.answers[2].iconUnselected
        )
    }

    @Test
    fun `mapper should map satisfied answers icon correctly`() {
        val groupQuestion = ZEMTGroupQuestionsMother.random()
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertEquals(
            R.drawable.zemt_ic_sentiment_satisfied_selected,
            groupQuestionUiModel!!.answers[1].iconSelected
        )
        assertEquals(
            R.drawable.zemt_ic_sentiment_satisfied,
            groupQuestionUiModel.answers[3].iconUnselected
        )
    }

    @Test
    fun `mapper should map excited answer icons correctly`() {
        val groupQuestion = ZEMTGroupQuestionsMother.random()
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertEquals(
            R.drawable.zemt_ic_sentiment_excited_selected,
            groupQuestionUiModel!!.answers[0].iconSelected
        )
        assertEquals(
            R.drawable.zemt_ic_sentiment_excited,
            groupQuestionUiModel.answers[4].iconUnselected
        )
    }

    @Test
    fun `mapper should map one two and three answers from left question`() {
        val questionId = ZEMTQuestionId(10)
        val idAnswerOne = ZEMTAnswerId(1)
        val idAnswerTwo = ZEMTAnswerId(2)
        val idAnswerThree = ZEMTAnswerId(3)
        val groupQuestion = ZEMTGroupQuestionsMother.apply(
            leftQuestion = ZEMTQuestionMother.apply(
                id = questionId,
                answers = listOf(
                    ZEMTAnswerOptionMother.apply(id = idAnswerThree, questionId = questionId, order = 3),
                    ZEMTAnswerOptionMother.apply(id = idAnswerOne, questionId = questionId, order = 1),
                    ZEMTAnswerOptionMother.apply(id = idAnswerTwo, questionId = questionId, order = 2)
                )
            )
        )
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertEquals(questionId, groupQuestionUiModel!!.answers[0].questionId)
        assertEquals(idAnswerOne, groupQuestionUiModel.answers[0].id)
        assertEquals(questionId, groupQuestionUiModel.answers[1].questionId)
        assertEquals(idAnswerTwo, groupQuestionUiModel.answers[1].id)
        assertEquals(questionId, groupQuestionUiModel.answers[1].questionId)
        assertEquals(idAnswerThree, groupQuestionUiModel.answers[2].id)
    }

    @Test
    fun `mapper should map four and five answers from right question`() {
        val questionId = ZEMTQuestionId(20)
        val idAnswerOne = ZEMTAnswerId(1)
        val idAnswerTwo = ZEMTAnswerId(2)
        val groupQuestion = ZEMTGroupQuestionsMother.apply(
            rightQuestion = ZEMTQuestionMother.apply(
                id = questionId,
                answers = listOf(
                    ZEMTAnswerOptionMother.apply(order = 1),
                    ZEMTAnswerOptionMother.apply(id = idAnswerOne, questionId = questionId, order = 2),
                    ZEMTAnswerOptionMother.apply(id = idAnswerTwo, questionId = questionId, order = 3)
                )
            )
        )
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()

        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)

        assertEquals(questionId, groupQuestionUiModel!!.answers[3].questionId)
        assertEquals(idAnswerOne, groupQuestionUiModel.answers[3].id)
        assertEquals(questionId, groupQuestionUiModel.answers[4].questionId)
        assertEquals(idAnswerTwo, groupQuestionUiModel.answers[4].id)
    }

    @Test
    fun `mapper should map neutral answer enabled false when maxNumberOfNeutralAnswersReached is true`() {
        val groupQuestion = ZEMTGroupQuestionsMother.apply(
            leftQuestion = ZEMTQuestionMother.apply(
                answers = listOf(
                    ZEMTAnswerOptionMother.apply(order = 1),
                    ZEMTAnswerOptionMother.apply(order = 2),
                    ZEMTAnswerOptionMother.apply(order = 3, value = 0)
                )
            )
        )
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()
        val groupQuestionUiModel = mapper.invoke(groupQuestion, true)
        assertFalse(groupQuestionUiModel!!.answers[2].enabled)
    }

    @Test
    fun `mapper should map neutral answer enabled true when maxNumberOfNeutralAnswersReached is false`() {
        val groupQuestion = ZEMTGroupQuestionsMother.apply(
            leftQuestion = ZEMTQuestionMother.apply(
                answers = listOf(
                    ZEMTAnswerOptionMother.apply(order = 1),
                    ZEMTAnswerOptionMother.apply(order = 2),
                    ZEMTAnswerOptionMother.apply(order = 3, value = 0)
                )
            )
        )
        val mapper = ZEMTDiscoverGroupQuestionsToUiModelMapper()
        val groupQuestionUiModel = mapper.invoke(groupQuestion, false)
        assertTrue(groupQuestionUiModel!!.answers[2].enabled)
    }

}