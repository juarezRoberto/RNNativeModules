package com.upax.zemytalents.ui.modules.apply.survey.mock

import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyQuestionUiModel
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyTalentUiModel
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId

internal object ZEMTMockApplySurveyData {

    fun getTalents(): List<ZEMTSurveyApplyTalentUiModel> {
        return listOf(
            ZEMTSurveyApplyTalentUiModel(
                id = 1,
                name = "Comunicador",
                order = 0,
                lottieUrl = "",
                finished = true,
                icon = getIconFromId(1),
                selected = true,
                question = getQuestion()
            ),
            ZEMTSurveyApplyTalentUiModel(
                id = 2,
                name = "Comunicador",
                order = 1,
                lottieUrl = "",
                finished = true,
                icon = getIconFromId(1),
                selected = false,
                question = getQuestion()
            ),
            ZEMTSurveyApplyTalentUiModel(
                id = 3,
                name = "Concentración",
                order = 2,
                lottieUrl = "",
                finished = false,
                icon = getIconFromId(2),
                selected = false,
                question = getQuestion()
            )
        )
    }

    private fun getQuestion(): ZEMTSurveyApplyQuestionUiModel {
        return ZEMTSurveyApplyQuestionUiModel(
            id = 1,
            order = 1,
            header = "Mi motivación",
            text = "¿Qué es lo que más te motiva?",
            answerOptions = getOptions(),
            isCompleted = false
        )
    }

    private fun getOptions(): List<ZEMTSurveyApplyAnswerOptionUiModel> {
        return listOf(
            ZEMTSurveyApplyAnswerOptionUiModel(
                id = 1,
                questionId = 1,
                order = 1,
                text = "Haciendo grandes amistades.",
                value = 1,
                status = ZEMTSurveyApplyAnswerOptionUiModel.Status.UNCHECKED
            ),
            ZEMTSurveyApplyAnswerOptionUiModel(
                id = 2,
                questionId = 2,
                order = 2,
                text = "Conociendo la vida y los secretos de los demás.",
                value = 3,
                status = ZEMTSurveyApplyAnswerOptionUiModel.Status.UNCHECKED
            ),
            ZEMTSurveyApplyAnswerOptionUiModel(
                id = 3,
                order = 3,
                questionId = 3,
                text = "Cultivando la relación de amistad durante el tiempo.",
                value = 3,
                status = ZEMTSurveyApplyAnswerOptionUiModel.Status.UNCHECKED
            )
        )
    }

}