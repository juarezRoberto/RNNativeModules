package com.upax.zemytalents.ui.modules.apply.survey.mapper

import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel

internal object ZEMTSurveyApplyAnswerStatusMapper {

    fun toUiModel(
        status: ZEMTSurveyApplyAnswerStatus
    ): ZEMTSurveyApplyAnswerOptionUiModel.Status {
        return when (status) {
            ZEMTSurveyApplyAnswerStatus.UNCHECKED -> {
                ZEMTSurveyApplyAnswerOptionUiModel.Status.UNCHECKED
            }

            ZEMTSurveyApplyAnswerStatus.CHECK_NEGATIVE -> {
                ZEMTSurveyApplyAnswerOptionUiModel.Status.CHECK_NEGATIVE
            }

            ZEMTSurveyApplyAnswerStatus.CHECK_POSITIVE -> {
                ZEMTSurveyApplyAnswerOptionUiModel.Status.CHECK_POSITIVE
            }
        }
    }

    fun toDomainModel(
        status: ZEMTSurveyApplyAnswerOptionUiModel.Status
    ): ZEMTSurveyApplyAnswerStatus {
        return when (status) {
            ZEMTSurveyApplyAnswerOptionUiModel.Status.UNCHECKED -> {
                ZEMTSurveyApplyAnswerStatus.UNCHECKED
            }

            ZEMTSurveyApplyAnswerOptionUiModel.Status.CHECK_NEGATIVE -> {
                ZEMTSurveyApplyAnswerStatus.CHECK_NEGATIVE
            }

            ZEMTSurveyApplyAnswerOptionUiModel.Status.CHECK_POSITIVE -> {
                ZEMTSurveyApplyAnswerStatus.CHECK_POSITIVE
            }
        }
    }

}