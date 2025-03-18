package com.upax.zemytalents.ui.modules.shared.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal interface ZEMTTalentUiModel {
    val id: Int
    val name: String
    @get:DrawableRes
    val icon: Int
    val finished: Boolean
    val selected: Boolean
    @get:DrawableRes
    val statusIcon: Int
    @get:Composable
    val statusIconColor: Color
    @get:Composable
    val colorStroke: Color
    @get:Composable
    val backgroundColor: Color
}