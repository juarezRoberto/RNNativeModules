package com.upax.zemytalents.ui.conversations.collaboratordetail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.data.local.icons.ZEMTIconParser
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.ui.conversations.collaboratordetail.model.ZEMTCollaboratorDetailMenuMock
import com.upax.zemytalents.ui.conversations.collaboratordetail.model.ZEMTCollaboratorDetailNavigation
import com.upax.zemytalents.ui.conversations.collaboratordetail.model.ZEMTCollaboratorNavigationType
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownGroup
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTCollaboratorDetailView(
    navigateTo: (ZEMTCollaboratorDetailNavigation) -> Unit,
    menuSectionList: List<ZEMTCollaboratorDetailSection>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        for (section in menuSectionList) {
            ZEMTText(
                text = section.sectionTitle,
                style = DesignSystem.style.TextAppearance_ZCDSApp_Header04,
                modifier = Modifier.padding(top = 24.dp)
            )

            val context = LocalContext.current
            section.items.forEachIndexed { index, item ->
                ZEMTDropdownGroup(
                    groupTitle = item.title,
                    groupIcon = item.icon,
                    modifier = Modifier.padding(top = if (index == 0) 12.dp else 16.dp),
                    isOpenable = false,
                    onGroupClick = {
                        navigateTo(
                            when (section.navigationType) {
                                ZEMTCollaboratorNavigationType.GoToConversation -> ZEMTCollaboratorDetailNavigation.GoToConversation(
                                    conversationTitle = item.title,
                                    conversationId = item.id
                                )

                                ZEMTCollaboratorNavigationType.GoToTalent -> ZEMTCollaboratorDetailNavigation.GoToTalent(
                                    subtitle = context.getString(R.string.zemt_dominant_no_dominant_talents)
                                )
                            }
                        )
                    }
                )
            }
        }
    }
}

data class ZEMTCollaboratorDetailSection(
    val sectionTitle: String,
    val navigationType: ZEMTCollaboratorNavigationType,
    val items: List<ZEMTCollaboratorDetailItem>
)

data class ZEMTCollaboratorDetailItem(
    val id: String,
    val title: String,
    @DrawableRes val icon: Int,
)

@Preview
@Composable
private fun ZEMTCollaboratorDetailViewPreview() {
    val context = LocalContext.current
    ZEMTCollaboratorDetailView(
        modifier = Modifier.padding(horizontal = 16.dp),
        navigateTo = {},
        menuSectionList = ZEMTCollaboratorDetailMenuMock.getMenuList(
            getString = { context.getString(it) },
            conversationList = listOf(
                ZEMTConversation(
                    conversationId = "1",
                    name = "Conversation 1",
                    description = "Description 1",
                    order = 1,
                    icon = ZEMTIconParser.FIJAR_OBJECTIVOS.iconId,
                    lottieUrl = ""
                ),
            )
        )
    )
}