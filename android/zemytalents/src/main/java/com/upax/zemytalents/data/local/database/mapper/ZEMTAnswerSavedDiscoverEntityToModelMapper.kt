package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.embedded.ZEMTAnswerSavedWithAnswerOption
import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerSavedDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId

internal class ZEMTAnswerSavedDiscoverEntityToModelMapper:
    Function1<ZEMTAnswerSavedWithAnswerOption, ZEMTAnswerSavedDiscover> {

    override fun invoke(answer: ZEMTAnswerSavedWithAnswerOption): ZEMTAnswerSavedDiscover {
        return ZEMTAnswerSavedDiscover(
            id = ZEMTAnswerId(answer.answerOption.answerOptionId),
            questionId = ZEMTQuestionId(answer.answerOption.questionId),
            groupQuestionIndex = answer.answerSaved.groupQuestionIndex,
            value = answer.answerOption.value,
            date = answer.answerSaved.date,
            location = ZEMTLocation(
                latitude = answer.answerSaved.latitude,
                longitude = answer.answerSaved.longitude
            )
        )
    }

}