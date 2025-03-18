package com.upax.zemytalents.ui.modules.apply.survey.mapper

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.domain.models.ZEMTAttachmentType
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestion
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestionOption
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyQuestionUiModel
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyTalentUiModel
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId

internal class ZEMTTalentsApplyToUiModelMapper :
    Function2<List<ZEMTSurveyTalent>, List<ZEMTTalent>, List<ZEMTSurveyApplyTalentUiModel>> {

    override fun invoke(
        surveyTalents: List<ZEMTSurveyTalent>,
        userTalents: List<ZEMTTalent>
    ): List<ZEMTSurveyApplyTalentUiModel> {
        val indexTalentSelected = surveyTalents.indexOfFirst { !it.finished }
        return surveyTalents.mapIndexed { index, talent ->
            ZEMTSurveyApplyTalentUiModel(
                id = talent.id,
                name = talent.name,
                order = talent.order,
                icon = getIconFromId(talent.id),
                lottieUrl = getTalentLottie(talent, userTalents),
                finished = talent.finished,
                selected = index == indexTalentSelected,
                question = mapQuestion(talent.questions.first())
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

    private fun mapQuestion(
        question: ZEMTSurveyTalentQuestion
    ): ZEMTSurveyApplyQuestionUiModel {
        return ZEMTSurveyApplyQuestionUiModel(
            id = question.id,
            order = question.order,
            header = question.header,
            text = question.text,
            isCompleted = question.completed,
            answerOptions = mapAnswerOptions(
                answers = question.answerOptions,
                questionId = question.id
            )
        )
    }

    private fun mapAnswerOptions(
        answers: List<ZEMTSurveyTalentQuestionOption>, questionId: Int
    ): List<ZEMTSurveyApplyAnswerOptionUiModel> {
        return answers.map { answer ->
            ZEMTSurveyApplyAnswerOptionUiModel(
                id = answer.id,
                questionId = questionId,
                order = answer.order,
                text = answer.text,
                value = answer.value,
                status = ZEMTSurveyApplyAnswerStatusMapper.toUiModel(answer.status)
            )
        }
    }


}