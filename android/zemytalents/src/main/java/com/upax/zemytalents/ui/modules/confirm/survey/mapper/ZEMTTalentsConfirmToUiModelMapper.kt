package com.upax.zemytalents.ui.modules.confirm.survey.mapper

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.domain.models.ZEMTAttachmentType
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestion
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestionOption
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmQuestionUiModel
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmTalentUiModel
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId

internal class ZEMTTalentsConfirmToUiModelMapper :
    Function3<List<ZEMTSurveyTalent>, List<ZEMTTalent>, Int?, List<ZEMTSurveyConfirmTalentUiModel>> {

    override fun invoke(
        surveyTalents: List<ZEMTSurveyTalent>,
        userTalents: List<ZEMTTalent>,
        selectedTalentId: Int?,
    ): List<ZEMTSurveyConfirmTalentUiModel> {
        return surveyTalents.mapIndexed { index, talent ->
            ZEMTSurveyConfirmTalentUiModel(
                id = talent.id,
                name = talent.name,
                order = talent.order,
                icon = getIconFromId(talent.id),
                lottieUrl = getTalentLottie(talent, userTalents),
                description = talent.description,
                finished = false,
                selected = if (selectedTalentId == null) index == 0 else talent.id == selectedTalentId,
                questions = mapQuestions(talent.questions)
            )
        }
    }

    private fun getTalentLottie(
        surveyTalent: ZEMTSurveyTalent,
        userTalents: List<ZEMTTalent>
    ): String {
        val userTalent = userTalents.find { it.id == surveyTalent.id } ?: return String.EMPTY
        val lottieUrl = userTalent.attachment.firstOrNull {
            it.type == ZEMTAttachmentType.LOTTIE
        }?.url.orEmpty()
        return lottieUrl
    }

    private fun mapQuestions(
        questions: List<ZEMTSurveyTalentQuestion>
    ): List<ZEMTSurveyConfirmQuestionUiModel> {
        return questions.map { question ->
            ZEMTSurveyConfirmQuestionUiModel(
                id = question.id,
                order = question.order,
                header = question.header,
                text = question.text,
                isCompleted = false,
                answerOptions = mapAnswerOptions(question.answerOptions)
            )
        }
    }

    private fun mapAnswerOptions(
        answers: List<ZEMTSurveyTalentQuestionOption>
    ): List<ZEMTSurveyConfirmAnswerOptionUiModel> {
        return answers.map { answer ->
            ZEMTSurveyConfirmAnswerOptionUiModel(
                id = answer.id,
                order = answer.order,
                text = answer.text,
                value = answer.value,
                isChecked = false
            )
        }
    }

}