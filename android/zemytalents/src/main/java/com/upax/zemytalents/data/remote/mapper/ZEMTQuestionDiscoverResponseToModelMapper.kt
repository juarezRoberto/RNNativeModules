package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.remote.responses.ZEMTQuestionDiscoverResponse
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId

internal class ZEMTQuestionDiscoverResponseToModelMapper(
    private val answerOptionResponseToModelMapper: ZEMTAnswerOptionResponseToModelMapper
) : Function1<ZEMTQuestionDiscoverResponse, ZEMTQuestionDiscover> {

    override fun invoke(question: ZEMTQuestionDiscoverResponse): ZEMTQuestionDiscover {
        return ZEMTQuestionDiscover(
            id = ZEMTQuestionId(question.id.orZero()),
            text = question.text.orEmpty(),
            order = question.order.orZero(),
            answers = answerOptionResponseToModelMapper.invoke(
                question.answerOptions,
                ZEMTQuestionId(question.id.orZero())
            )
        )
    }

}