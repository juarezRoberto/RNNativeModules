package com.upax.zemytalents.ui.modules.confirm.mock

import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmQuestionUiModel
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmTalentUiModel
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId
import com.upax.zemytalents.ui.modules.shared.model.ZEMTTalentUiModel

internal object ZEMTMockConfirmSurveyData {

    fun getTalents(): List<ZEMTSurveyConfirmTalentUiModel> {
        return listOf(
            ZEMTSurveyConfirmTalentUiModel(
                id = 1,
                name = "Comunicador",
                order = 0,
                description = "Some description",
                finished = true,
                questions = getQuestions(),
                icon = getIconFromId(1),
                selected = true
            ),
            ZEMTSurveyConfirmTalentUiModel(
                id = 2,
                name = "Comunicador",
                description = "Some description",
                order = 1,
                finished = true,
                questions = getQuestions(),
                icon = getIconFromId(1),
                selected = false
            ),
            ZEMTSurveyConfirmTalentUiModel(
                id = 3,
                name = "Concentración",
                order = 2,
                description = "Some description",
                finished = false,
                questions = getQuestions(),
                icon = getIconFromId(2),
                selected = false
            )
        )
    }

    fun getQuestions(): List<ZEMTSurveyConfirmQuestionUiModel> {
        return listOf(
            ZEMTSurveyConfirmQuestionUiModel(
                id = 1,
                order = 1,
                header = "Mi motivación",
                text = "¿Qué es lo que más te motiva?",
                answerOptions = getOptions(),
                isCompleted = false
            ),
            ZEMTSurveyConfirmQuestionUiModel(
                id = 1,
                order = 1,
                header = "Preguntas de confirmación",
                text = "¿Cómo resuelves los problemas?",
                answerOptions = getOptions(),
                isCompleted = false
            )
        )
    }

    fun getOptions(): List<ZEMTSurveyConfirmAnswerOptionUiModel> {
        return listOf(
            ZEMTSurveyConfirmAnswerOptionUiModel(
                id = 1,
                order = 1,
                text = "Haciendo grandes amistades.",
                value = 1,
                isChecked = false
            ),
            ZEMTSurveyConfirmAnswerOptionUiModel(
                id = 2,
                order = 2,
                text = "Conociendo la vida y los secretos de los demás.",
                value = 3,
                isChecked = false
            ),
            ZEMTSurveyConfirmAnswerOptionUiModel(
                id = 3,
                order = 3,
                text = "Cultivando la relación de amistad durante el tiempo.",
                value = 3,
                isChecked = false
            )
        )
    }
}