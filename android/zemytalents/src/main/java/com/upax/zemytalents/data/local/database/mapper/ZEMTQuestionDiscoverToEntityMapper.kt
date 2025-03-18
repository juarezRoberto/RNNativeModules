package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover

internal class ZEMTQuestionDiscoverToEntityMapper {

    fun invoke(
        question: ZEMTQuestionDiscover,
        surveyId: ZEMTSurveyId,
        groupQuestionIndex: ZEMTGroupQuestionIndexDiscover
    ): ZEMTQuestionDiscoverEntity {
        return ZEMTQuestionDiscoverEntity(
            questionId = question.id.value,
            text = question.text,
            surveyId = surveyId.value.toInt(),
            order = question.order,
            groupQuestionIndex = groupQuestionIndex.value,
            lastSeen = 0
        )
    }

}