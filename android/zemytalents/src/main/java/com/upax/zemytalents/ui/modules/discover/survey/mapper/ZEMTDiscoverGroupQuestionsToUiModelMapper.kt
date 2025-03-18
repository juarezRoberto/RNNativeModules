package com.upax.zemytalents.ui.modules.discover.survey.mapper

import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerSide
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerUiModel
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverQuestionUiModel
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverGroupQuestionsUiModel

internal class ZEMTDiscoverGroupQuestionsToUiModelMapper :
    Function2<ZEMTGroupQuestionsDiscover?, Boolean, ZEMTDiscoverGroupQuestionsUiModel?> {

    override fun invoke(
        groupQuestions: ZEMTGroupQuestionsDiscover?,
        maxNumberOfNeutralAnswersReached: Boolean
    ): ZEMTDiscoverGroupQuestionsUiModel? {
        if (groupQuestions == null) return null
        return ZEMTDiscoverGroupQuestionsUiModel(
            index = groupQuestions.index,
            leftQuestion = mapQuestion(groupQuestions.leftQuestion),
            rightQuestion = mapQuestion(groupQuestions.rightQuestion),
            answers = mapAnswers(
                leftQuestion = groupQuestions.leftQuestion,
                rightQuestion = groupQuestions.rightQuestion,
                maxNumberOfNeutralAnswersReached
            )
        )
    }

    private fun mapQuestion(question: ZEMTQuestionDiscover): ZEMTDiscoverQuestionUiModel {
        return ZEMTDiscoverQuestionUiModel(
            id = question.id,
            text = question.text
        )
    }

    private fun mapAnswers(
        leftQuestion: ZEMTQuestionDiscover,
        rightQuestion: ZEMTQuestionDiscover,
        maxNumberOfNeutralAnswersReached: Boolean
    ): List<ZEMTDiscoverAnswerUiModel> = try {
        buildList {
            mapAnswer(
                leftQuestion.id, leftQuestion.answers.find { it.order == 1 }!!, index = 1
            ).also { add(it) }
            mapAnswer(
                leftQuestion.id, leftQuestion.answers.find { it.order == 2 }!!, index = 2
            ).also { add(it) }
            mapAnswer(
                leftQuestion.id,
                leftQuestion.answers.find { it.order == 3 }!!,
                index = 3,
                enabled = !maxNumberOfNeutralAnswersReached
            ).also { add(it) }
            mapAnswer(
                rightQuestion.id, rightQuestion.answers.find { it.order == 2 }!!, index = 4
            ).also { add(it) }
            mapAnswer(
                rightQuestion.id, rightQuestion.answers.find { it.order == 3 }!!, index = 5
            ).also { add(it) }
        }
    } catch (e: NullPointerException) {
        emptyList()
    } catch (e: IndexOutOfBoundsException) {
        emptyList()
    }

    private fun mapAnswer(
        questionId: ZEMTQuestionId,
        answer: ZEMTAnswerOptionDiscover,
        index: Int,
        enabled: Boolean = true,
    ): ZEMTDiscoverAnswerUiModel {
        val icons = mapIconsAnswer(index)
        return ZEMTDiscoverAnswerUiModel(
            id = answer.id,
            questionId = questionId,
            text = answer.text,
            order = answer.order,
            enabled = if (answer.isNeutralAnswer) enabled else true,
            side = mapSide(index),
            color = mapColorAnswer(index),
            iconSelected = icons.first,
            iconUnselected = icons.second,
        )
    }

    private fun mapSide(index: Int): ZEMTDiscoverAnswerSide {
        return when (index) {
            1, 2 -> ZEMTDiscoverAnswerSide.LEFT
            3 -> ZEMTDiscoverAnswerSide.MIDDLE
            4, 5 -> ZEMTDiscoverAnswerSide.RIGHT
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun mapIconsAnswer(index: Int): Pair<Int, Int> {
        return when (index) {
            1, 5 -> R.drawable.zemt_ic_sentiment_excited_selected to R.drawable.zemt_ic_sentiment_excited
            2, 4 -> R.drawable.zemt_ic_sentiment_satisfied_selected to R.drawable.zemt_ic_sentiment_satisfied
            else -> R.drawable.zemt_ic_sentiment_neutral_selected to R.drawable.zemt_ic_sentiment_neutral
        }
    }

    private fun mapColorAnswer(index: Int): Int {
        return when (index) {
            1, 5 -> com.upax.zcdesignsystem.R.color.zcds_success
            2, 4 -> com.upax.zcdesignsystem.R.color.zcds_warning
            3 -> com.upax.zcdesignsystem.R.color.zcds_very_light_gray_200
            else -> throw IndexOutOfBoundsException()
        }
    }

}