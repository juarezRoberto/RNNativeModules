package com.upax.zemytalents.data.remote.mapper

internal object ZEMTDiscoverSurveyResponseToModelMapperMother {

    fun apply(
        breakResponseToModelMapper: ZEMTBreakDiscoverResponseToModelMapper =
            ZEMTBreakDiscoverResponseToModelMapper(
                attachmentResponseToModelMapper = ZEMTAttachmentDiscoverResponseToModelMapper()
            ),
        groupQuestionsResponseToModelMapper: ZEMTGroupQuestionsDiscoverResponseToModelMapper =
            ZEMTGroupQuestionsDiscoverResponseToModelMapper(
                questionResponseToModelMapper = ZEMTQuestionDiscoverResponseToModelMapper(
                    answerOptionResponseToModelMapper = ZEMTAnswerOptionResponseToModelMapper()
                )
            )
    ): ZEMTSurveyDiscoverResponseToModelMapper {
        return ZEMTSurveyDiscoverResponseToModelMapper(
            breakResponseToModelMapper = breakResponseToModelMapper,
            groupQuestionsResponseToModelMapper = groupQuestionsResponseToModelMapper
        )
    }

    fun random() = apply()

}