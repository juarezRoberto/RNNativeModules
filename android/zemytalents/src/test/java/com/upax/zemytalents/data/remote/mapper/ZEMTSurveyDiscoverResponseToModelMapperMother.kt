package com.upax.zemytalents.data.remote.mapper

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

internal object ZEMTSurveyDiscoverResponseToModelMapperMother {

    fun apply(
        breakResponseToModelMapper: ZEMTBreakDiscoverResponseToModelMapper = ZEMTBreakDiscoverResponseToModelMapper(
            ZEMTAttachmentDiscoverResponseToModelMapper()
        ),
        groupQuestionsResponseToModelMapper: ZEMTGroupQuestionsDiscoverResponseToModelMapper = ZEMTGroupQuestionsDiscoverResponseToModelMapper(
            ZEMTQuestionDiscoverResponseToModelMapper(ZEMTAnswerOptionResponseToModelMapper())
        )
    ): ZEMTSurveyDiscoverResponseToModelMapper {
        return ZEMTSurveyDiscoverResponseToModelMapper(
            breakResponseToModelMapper = breakResponseToModelMapper,
            groupQuestionsResponseToModelMapper = groupQuestionsResponseToModelMapper
        )
    }

    fun random() = apply()

}