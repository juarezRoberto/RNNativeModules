package com.upax.zemytalents.domain.models.modules.discover

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ZEMTAnswerOptionDiscoverTest {

    @Test
    fun `answer with value 0 should be neutral`() {
        val answer = ZEMTAnswerOptionDiscoverMother.apply(value = 0)
        assertTrue(answer.isNeutralAnswer)
    }

    @Test
    fun `answer with value different to 0 should not be neutral`() {
        val answer = ZEMTAnswerOptionDiscoverMother.apply(value = 5)
        assertFalse(answer.isNeutralAnswer)
    }

}