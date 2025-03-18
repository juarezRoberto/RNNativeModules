package com.upax.zemytalents.ui.modules.confirm.survey.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.upax.zccommon.extensions.EMPTY
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.shared.model.ZEMTTalentUiModel
import com.upax.zcdesignsystem.R as RDS

internal data class ZEMTSurveyConfirmTalentUiModel(
    override val id: Int,
    override val name: String,
    val order: Int,
    @DrawableRes override val icon: Int,
    val lottieUrl: String = String.EMPTY,
    val description: String,
    override val finished: Boolean,
    override val selected: Boolean,
    val questions: List<ZEMTSurveyConfirmQuestionUiModel>,
) : ZEMTTalentUiModel {

    @DrawableRes
    override val statusIcon = when {
        finished -> RDS.drawable.zcds_ic_check_solid
        selected -> R.drawable.zemt_ic_padlock_open_filled_white
        else -> R.drawable.zemt_ic_padlock_closed_outlined
    }

    override val colorStroke: Color
        @Composable get() = if (finished) {
            colorResource(id = RDS.color.zcds_success)
        } else {
            Color(ZCDSColorUtils.getPrimaryColor())
        }

    val allQuestionsAnswered = questions.all { it.isCompleted }

    override val statusIconColor: Color
        @Composable get() = if (finished) {
            colorResource(id = RDS.color.zcds_white)
        } else {
            Color(ZCDSColorUtils.getPrimaryColor())
        }

    override val backgroundColor: Color
        @Composable get() = if (finished) {
            Color(ZCDSColorUtils.getPrimaryColor())
        } else {
            colorResource(id = RDS.color.zcds_very_light_gray_200)
        }

}