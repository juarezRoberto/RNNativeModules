package com.upax.zemytalents.ui.modules.discover.survey.mock

import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverUiState
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerSide
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerUiModel
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverGroupQuestionsUiModel
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverQuestionUiModel

internal object ZEMTDiscoverMockData {

    val mockAnswers = listOf(
        ZEMTDiscoverAnswerUiModel(
            id = ZEMTAnswerId(1),
            questionId = ZEMTQuestionId(1),
            text = "Me describe completamente",
            order = 1,
            enabled = true,
            iconUnselected = R.drawable.zemt_ic_sentiment_excited,
            iconSelected = R.drawable.zemt_ic_sentiment_excited_selected,
            color = com.upax.zcdesignsystem.R.color.zcds_success,
            side = ZEMTDiscoverAnswerSide.LEFT
        ),
        ZEMTDiscoverAnswerUiModel(
            id = ZEMTAnswerId(1),
            questionId = ZEMTQuestionId(1),
            text = "A veces me describe",
            order = 2,
            enabled = true,
            iconUnselected = R.drawable.zemt_ic_sentiment_satisfied,
            iconSelected = R.drawable.zemt_ic_sentiment_satisfied_selected,
            color = com.upax.zcdesignsystem.R.color.zcds_warning,
            side = ZEMTDiscoverAnswerSide.LEFT
        ),
        ZEMTDiscoverAnswerUiModel(
            id = ZEMTAnswerId(1),
            questionId = ZEMTQuestionId(1),
            text = "Ninguna me describe / Ambas me describen",
            order = 3,
            enabled = false,
            iconUnselected = R.drawable.zemt_ic_sentiment_neutral,
            iconSelected = R.drawable.zemt_ic_sentiment_neutral_selected,
            color = com.upax.zcdesignsystem.R.color.zcds_very_light_gray_200,
            side = ZEMTDiscoverAnswerSide.MIDDLE
        ),
        ZEMTDiscoverAnswerUiModel(
            id = ZEMTAnswerId(1),
            questionId = ZEMTQuestionId(1),
            text = "A veces me describe",
            order = 4,
            enabled = true,
            iconUnselected = R.drawable.zemt_ic_sentiment_satisfied,
            iconSelected = R.drawable.zemt_ic_sentiment_satisfied_selected,
            color = com.upax.zcdesignsystem.R.color.zcds_warning,
            side = ZEMTDiscoverAnswerSide.RIGHT
        ),
        ZEMTDiscoverAnswerUiModel(
            id = ZEMTAnswerId(1),
            questionId = ZEMTQuestionId(1),
            text = "Me describe completamente",
            order = 5,
            enabled = true,
            iconUnselected = R.drawable.zemt_ic_sentiment_excited,
            iconSelected = R.drawable.zemt_ic_sentiment_excited_selected,
            color = com.upax.zcdesignsystem.R.color.zcds_success,
            side = ZEMTDiscoverAnswerSide.RIGHT
        )
    )

    val mockUiState = ZEMTSurveyDiscoverUiState(
        currentGroupQuestions = ZEMTDiscoverGroupQuestionsUiModel(
            index = ZEMTGroupQuestionIndexDiscover(1),
            leftQuestion = ZEMTDiscoverQuestionUiModel(
                id = ZEMTQuestionId(1),
                text = "Los demás acuden a mi para verificar que su información sea exacta, bien documentada"
            ),
            rightQuestion = ZEMTDiscoverQuestionUiModel(
                id = ZEMTQuestionId(1),
                text = "Me siento triste cuando no le caigo bien a alguien"
            ),
            answers = mockAnswers
        ),
        totalGroupQuestionsAnswered = 10,
        totalGroupQuestions = 100
    )

}