package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionMother
import org.junit.Assert.assertEquals
import org.junit.Test

class ZEMTAnswerOptionDiscoverToEntityMapperTest {

    @Test
    fun `mapper should map answer correctly`() {

        val answer = ZEMTAnswerOptionMother.random()
        val mapper = ZEMTAnswerDiscoverToEntityMapper()

        val answerEntity = mapper.invoke(answer)

        assertEquals(
            ZEMTAnswerOptionDiscoverEntity(
                answerOptionId = answer.id.value,
                questionId = answer.questionId.value,
                order = answer.order,
                text = answer.text,
                value = answer.value
            ),
            answerEntity
        )
    }


}