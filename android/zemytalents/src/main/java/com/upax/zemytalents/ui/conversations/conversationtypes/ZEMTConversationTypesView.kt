package com.upax.zemytalents.ui.conversations.conversationtypes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.data.local.icons.ZEMTIconParser
import com.upax.zemytalents.data.mock.ZEMTMockConversationList2
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownGroup
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
internal fun ZEMTConversationTypesView(
    conversationList: List<ZEMTConversation>,
    modifier: Modifier = Modifier,
    showBottomSheet: (ZEMTConversation) -> Unit = {}
) {

    Column(modifier = modifier) {
        ZEMTText(
            text = stringResource(id = R.string.zemt_conversations_touch_conversation_for_more_info),
            style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
            modifier = Modifier.padding(top = 24.dp)
        )
        conversationList.forEachIndexed { index, conversation ->
            ZEMTDropdownGroup(
                groupTitle = conversation.name,
                groupIcon = ZEMTIconParser.parseIcon(conversation.icon, index),
                modifier = Modifier.padding(top = if (index == 0) 24.dp else 16.dp),
                isOpenable = false,
                onGroupClick = {
                    showBottomSheet(conversation)
                }
            )
        }
    }
}

@Preview
@Composable
private fun ZEMTConversationTypesViewPreview() {
    ZEMTConversationTypesView(conversationList = ZEMTMockConversationList2())
}