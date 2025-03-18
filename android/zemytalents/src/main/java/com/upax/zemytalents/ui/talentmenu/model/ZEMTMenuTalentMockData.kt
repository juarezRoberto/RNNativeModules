package com.upax.zemytalents.ui.talentmenu.model

import android.content.Context
import com.upax.zemytalents.R

internal object ZEMTMenuTalentMockData {
    fun Context.getMenuList(collaboratorText: String) = listOf(
        ZEMTMenuOption(
            title = getString(R.string.zemt_my_talents),
            icon = R.drawable.zemt_ic_person_play,
            navigateTo = ZEMTMenuTalentNavigation.GO_MY_TALENTS
        ),
        ZEMTMenuOption(
            title = getString(R.string.zemt_talent_menu_converstions),
            icon = R.drawable.zemt_ic_school,
            navigateTo = ZEMTMenuTalentNavigation.NONE,
            children = listOf(
                ZEMTMenuOption(
                    title = collaboratorText,
                    icon = R.drawable.zemt_ic_adaptive_audio_mic,
                    navigateTo = ZEMTMenuTalentNavigation.GO_COLLABORATORS
                ),
                ZEMTMenuOption(
                    title = getString(R.string.zemt_talent_menu_converstion_types),
                    icon = R.drawable.zemt_ic_record_voice_over,
                    navigateTo = ZEMTMenuTalentNavigation.GO_CONVERSATION_TYPES
                )
            )
        )
    )
}