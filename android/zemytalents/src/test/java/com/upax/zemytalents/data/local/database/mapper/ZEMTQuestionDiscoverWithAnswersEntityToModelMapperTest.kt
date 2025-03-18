package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.entity.ZEMTAnswerOptionEntityMother
import com.upax.zemytalents.data.local.database.entity.ZEMTQuestionEntityMother
import com.upax.zemytalents.data.local.database.entity.ZEMTQuestionWithAnswersMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import org.junit.Assert.assertEquals
import org.junit.Test

class ZEMTQuestionDiscoverWithAnswersEntityToModelMapperTest {

    @Test
    fun `mapper should map question correctly`() {
        val questionEntity = ZEMTQuestionEntityMother.random()
        val questionWithAnswers = ZEMTQuestionWithAnswersMother.apply(question = questionEntity)
        val mapper = ZEMTQuestionWithAnswersEntityToModelMapper()

        val question = mapper.invoke(questionWithAnswers)

        assertEquals(questionEntity.questionId, question.id.value)
        assertEquals(questionEntity.text, question.text)
    }

    @Test
    fun `mapper should map answers correctly`() {
        val answerEntity = ZEMTAnswerOptionEntityMother.random()
        val questionWithAnswers = ZEMTQuestionWithAnswersMother.apply(
            answers = listOf(answerEntity)
        )
        val mapper = ZEMTQuestionWithAnswersEntityToModelMapper()

        val question = mapper.invoke(questionWithAnswers)

        val answerExpected = ZEMTAnswerOptionDiscover(
            id = ZEMTAnswerId(answerEntity.answerOptionId),
            questionId = ZEMTQuestionId(answerEntity.questionId),
            text = answerEntity.text,
            order = answerEntity.order,
            value = answerEntity.value
        )
        assertEquals(answerExpected, question.answers.first())
    }

    @Test
    fun `mapper should order answers by position`() {
        val answerEntity1 = ZEMTAnswerOptionEntityMother.apply(position = 3)
        val answerEntity2 = ZEMTAnswerOptionEntityMother.apply(position = 1)
        val answerEntity3 = ZEMTAnswerOptionEntityMother.apply(position = 2)
        val answerEntity4 = ZEMTAnswerOptionEntityMother.apply(position = 4)
        val questionWithAnswers = ZEMTQuestionWithAnswersMother.apply(
            answers = listOf(answerEntity1, answerEntity2, answerEntity3, answerEntity4).shuffled()
        )
        val mapper = ZEMTQuestionWithAnswersEntityToModelMapper()

        val question = mapper.invoke(questionWithAnswers)

        val questionsIdsExpected = listOf(
            answerEntity2.answerOptionId,
            answerEntity3.answerOptionId,
            answerEntity1.answerOptionId,
            answerEntity4.answerOptionId,
        )
        assertEquals(questionsIdsExpected, question.answers.map { it.id.value })
    }

}