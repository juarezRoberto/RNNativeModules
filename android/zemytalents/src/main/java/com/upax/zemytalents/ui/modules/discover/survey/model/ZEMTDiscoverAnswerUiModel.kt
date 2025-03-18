package com.upax.zemytalents.ui.modules.discover.survey.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId

internal data class ZEMTDiscoverAnswerUiModel(
    val id: ZEMTAnswerId,
    val questionId: ZEMTQuestionId,
    val text: String,
    val order: Int,
    val enabled: Boolean,
    @DrawableRes val iconSelected: Int,
    @DrawableRes val iconUnselected: Int,
    val side: ZEMTDiscoverAnswerSide,
    @ColorRes val color: Int
)

internal enum class ZEMTDiscoverAnswerSide {
    LEFT, RIGHT, MIDDLE
}