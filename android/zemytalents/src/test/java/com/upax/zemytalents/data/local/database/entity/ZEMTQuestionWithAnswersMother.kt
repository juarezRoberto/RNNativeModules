package com.upax.zemytalents.data.local.database.entity

import com.upax.zemytalents.data.local.database.embedded.ZEMTQuestionWithAnswers
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTQuestionWithAnswersMother {

    fun apply(
        question: ZEMTQuestionDiscoverEntity = ZEMTQuestionEntityMother.random(),
        answers: List<ZEMTAnswerOptionDiscoverEntity> = ZEMTRandomValuesUtil.getRandomIntRange().map {
            ZEMTAnswerOptionEntityMother.random()
        }
    ): ZEMTQuestionWithAnswers {
        return ZEMTQuestionWithAnswers(
            question = question,
            answers = answers
        )
    }

    fun random() = apply()

}