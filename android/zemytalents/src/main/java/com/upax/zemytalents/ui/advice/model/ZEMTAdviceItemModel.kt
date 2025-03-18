package com.upax.zemytalents.ui.advice.model

import androidx.annotation.DrawableRes

data class ZEMTAdviceItemModel(
    @DrawableRes val resourceId: Int,
    val description: String,
)