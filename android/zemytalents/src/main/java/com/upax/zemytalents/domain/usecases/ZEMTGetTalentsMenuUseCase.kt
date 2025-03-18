package com.upax.zemytalents.domain.usecases

import android.content.Context
import com.upax.zemytalents.ui.talentmenu.model.ZEMTMenuOption
import com.upax.zemytalents.ui.talentmenu.model.ZEMTMenuTalentMockData.getMenuList
import com.upax.zemytalents.ui.talentmenu.model.ZEMTMenuTalentNavigation

class ZEMTGetTalentsMenuUseCase(private val context: Context) {
    operator fun invoke(
        hasCollaboratorsInCharge: Boolean,
        collaboratorText: String
    ): List<ZEMTMenuOption> {
        val menu = context.getMenuList(collaboratorText)
        val filteredMenu =
            if (!hasCollaboratorsInCharge) menu.filter { it.navigateTo != ZEMTMenuTalentNavigation.NONE } else menu
        return filteredMenu
    }
}