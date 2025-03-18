package com.upax.zemytalents.ui.modules.apply.survey.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.shared.model.ZEMTTalentUiModel
import com.upax.zcdesignsystem.R as RDS

internal data class ZEMTSurveyApplyTalentUiModel(
    override val id: Int,
    override val name: String,
    override val finished: Boolean,
    override val selected: Boolean,
    val lottieUrl: String,
    val order: Int,
    @DrawableRes override val icon: Int,
    val question: ZEMTSurveyApplyQuestionUiModel
) : ZEMTTalentUiModel {

    @DrawableRes
    override val statusIcon = when {
        finished -> R.drawable.zemt_ic_star
        else -> RDS.drawable.zcds_ic_check_solid
    }

    val allAnswersQuestionChecked = question.allAnswersChecked

    val atLeastOneAnswerChecked = question.atLeastOneAnswerChecked

    override val colorStroke: Color
        @Composable get() = if (finished) {
            colorResource(id = RDS.color.zcds_warning)
        } else {
            colorResource(id = RDS.color.zcds_success)
        }

    fun finishTalent() = copy(finished = true, selected = false)

    override val statusIconColor: Color
        @Composable get() = colorResource(id = RDS.color.zcds_white)

    override val backgroundColor: Color
        @Composable get() = Color(ZCDSColorUtils.getPrimaryColor())

}