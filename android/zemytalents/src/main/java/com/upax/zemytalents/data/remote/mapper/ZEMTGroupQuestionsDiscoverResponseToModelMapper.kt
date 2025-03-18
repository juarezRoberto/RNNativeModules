package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.remote.responses.ZEMTGroupQuestionDiscoverResponse
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover

internal class ZEMTGroupQuestionsDiscoverResponseToModelMapper(
    private val questionResponseToModelMapper: ZEMTQuestionDiscoverResponseToModelMapper
) : Function1<List<ZEMTGroupQuestionDiscoverResponse?>?, List<ZEMTGroupQuestionsDiscover>> {

    override fun invoke(groupQuestions: List<ZEMTGroupQuestionDiscoverResponse?>?): List<ZEMTGroupQuestionsDiscover> {
        return groupQuestions?.mapNotNull { group ->
            if (group == null || group.questions.isNullOrEmpty() || group.questions.size < 2) {
                return@mapNotNull null
            }
            ZEMTGroupQuestionsDiscover(
                index = ZEMTGroupQuestionIndexDiscover(group.index.orZero()),
                leftQuestion = questionResponseToModelMapper.invoke(group.questions[0]!!),
                rightQuestion = questionResponseToModelMapper.invoke(group.questions[1]!!)
            )
        } ?: emptyList()
    }

}