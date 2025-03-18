package com.upax.zemytalents.ui.conversations.talentsresume

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.domain.models.ZEMTAttachmentType
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.ZEMTTalents
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.ui.conversations.talentsresume.composables.ZEMTConversationsView
import com.upax.zemytalents.ui.conversations.talentsresume.composables.ZEMTDominantTalents
import com.upax.zemytalents.ui.conversations.talentsresume.composables.ZEMTTabLayout
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTTalentsResumeUiModel
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId
import com.upax.zemytalents.ui.modules.shared.mock.ZEMTMockModulesData
import com.upax.zemytalents.ui.shared.composables.ZEMTBottomSheet
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownGroup

@Composable
internal fun ZEMTTalentsResumeView(
    userInfo: ZEMTTalentsResumeUiModel,
    modifier: Modifier = Modifier,
    onInfoButtonClick: () -> Unit = {},
    onConversationClick: (ZEMTConversation) -> Unit = {},
    onSelectedTabChange: (String) -> Unit = {}
) {
    var selectedTalent by remember { mutableStateOf<ZEMTTalent?>(null) }

    if (selectedTalent != null)
        ZEMTBottomSheet(
            title = selectedTalent?.name.orEmpty(),
            description = selectedTalent?.description.orEmpty(),
            lottieUrl = selectedTalent?.attachment?.firstOrNull { it.type == ZEMTAttachmentType.LOTTIE }?.url.orEmpty(),
            onDismissRequest = { selectedTalent = null },
            titleIcon = selectedTalent?.getIconFromId(),
            enableDismissButton = false
        )

    Column(
        modifier = modifier
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        key(userInfo.tabList.joinToString()) {
            ZEMTTabLayout(
                tabList = userInfo.tabList,
                onTabSelected = onSelectedTabChange,
                selectedTab = userInfo.selectedTab
            )
        }

        when (userInfo.selectedTab) {
            userInfo.tabList.firstOrNull() -> {
                ZEMTDominantTalents(
                    userName = userInfo.userName,
                    userProfileUrl = userInfo.userProfileUrl,
                    talentList = userInfo.talentList.dominantTalents.map { it.copy(isTempTalent = false) },
                    onTalentClick = { selectedTalent = it },
                    viewType = userInfo.viewType
                )
            }

            ZEMTTalentsResumeFragment.ZEMTTabOptionType.NO_DOMINANT.title -> {
                Spacer(modifier = Modifier.height(16.dp))
                userInfo.talentList.notDominantTalents.take(10).forEachIndexed { index, talent ->
                    ZEMTDropdownGroup(
                        groupTitle = "${index + 1}. ${talent.name}",
                        groupIcon = talent.getIconFromId(),
                        isOpenable = false,
                        onGroupClick = {
                            selectedTalent = talent
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            ZEMTTalentsResumeFragment.ZEMTTabOptionType.CONVERSATIONS.title -> {
                ZEMTConversationsView(
                    userInfo.conversations,
                    modifier = Modifier
                        .padding(top = 24.dp),
                    onInfoButtonClick = onInfoButtonClick,
                    onConversationClick = onConversationClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun ZEMTTalentsResumeViewPreview() {
    ZEMTTalentsResumeView(
        userInfo = ZEMTTalentsResumeUiModel(
            "David Martinez",
            "",
            tabList = listOf("Dominantes", "No dominantes"),
            talentList = ZEMTTalents(ZEMTMockModulesData.getTalents(), emptyList())
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    )
}