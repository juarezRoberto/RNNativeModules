package com.upax.zemytalents.ui.talentmenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTDropDownDirection
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownGroup
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownItem
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.talentmenu.model.ZEMTMenuOption
import com.upax.zemytalents.ui.talentmenu.model.ZEMTMenuTalentNavigation
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTMenuTalentView(
    userName: String,
    description: String,
    navigateTo: (ZEMTMenuTalentNavigation) -> Unit,
    modifier: Modifier = Modifier,
    options: List<ZEMTMenuOption> = emptyList(),
    canMakeConversation: Boolean = false
) {
    Column(modifier = modifier) {
        ZEMTText(
            text = stringResource(R.string.zemt_greeting_user, userName),
            style = DesignSystem.style.TextAppearance_ZCDSApp_Header04
        )
        ZEMTText(
            text = description,
            style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        for (option in options) {
            ZEMTDropdownGroup(
                modifier = Modifier
                    .padding(top = 16.dp),
                groupTitle = option.title,
                groupIcon = option.icon,
                isOpenable = option.children.isNotEmpty(),
                closedIconDirection = if (option.children.isEmpty()) ZEMTDropDownDirection.RIGHT else ZEMTDropDownDirection.DOWN,
                openedIconDirection = if (option.children.isEmpty()) ZEMTDropDownDirection.DOWN else ZEMTDropDownDirection.UP,
                onGroupClick = {
                    navigateTo(option.navigateTo)
                }
            ) {
                Column(Modifier.padding(it)) {
                    for (child in option.children) {
                        ZEMTDropdownItem(
                            itemIcon = child.icon,
                            itemText = child.title,
                            modifier = Modifier
                                .padding(start = 28.dp)
                                .padding(vertical = 12.dp),
                            onItemClick = {
                                navigateTo(child.navigateTo)
                            }
                        )
                    }
                }

            }
        }
        if (canMakeConversation) {
            Spacer(modifier = Modifier.weight(1f, true))
            ZEMTButton(
                text = stringResource(R.string.zemt_conversations_start),
                modifier = Modifier
                    .padding(bottom = dimensionResource(DesignSystem.dimen.zcds_margin_padding_size_large))
                    .fillMaxWidth(),
                onClick = { navigateTo(ZEMTMenuTalentNavigation.MAKE_CONVERSATION) }
            )
        }
    }
}

@Preview(name = "Preview school")
@Composable
private fun ZEMTMenuTalentViewPreview() {
    ZEMTMenuTalentView(
        userName = "Fernando",
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp),
        description = stringResource(R.string.zemt_talent_menu_info_school),
        navigateTo = {}
    )
}

@Preview(name = "Preview company")
@Composable
private fun ZEMTMenuTalentViewPreview2() {
    ZEMTMenuTalentView(
        userName = "Fernando",
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp),
        description = stringResource(R.string.zemt_talent_menu_info_company),
        navigateTo = {}
    )
}