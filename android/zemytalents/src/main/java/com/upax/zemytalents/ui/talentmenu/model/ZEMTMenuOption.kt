package com.upax.zemytalents.ui.talentmenu.model

import androidx.annotation.DrawableRes

data class ZEMTMenuOption(
    val title: String,
    @DrawableRes val icon: Int,
    val navigateTo: ZEMTMenuTalentNavigation = ZEMTMenuTalentNavigation.NONE,
    val children: List<ZEMTMenuOption> = emptyList()
)
