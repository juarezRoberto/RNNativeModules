package com.upax.zemytalents.ui.conversations.talentsresume.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zemytalents.R
import com.upax.zemytalents.data.mock.ZEMTMockConversationList2
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownGroup
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
internal fun ZEMTConversationsView(
    conversations: List<ZEMTConversation>,
    modifier: Modifier = Modifier,
    onInfoButtonClick: () -> Unit = {},
    onConversationClick: (ZEMTConversation) -> Unit = {}
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            ZEMTText(
                text = stringResource(R.string.zemt_collaborator_detail_talent_section_conversation_record_title),
                style = com.upax.zcdesignsystem.R.style.TextAppearance_ZCDSApp_Header04,
                modifier = Modifier.weight(1f, true)
            )
            Icon(
                painter = painterResource(DesignSystem.drawable.zcds_ic_information_circle_outlined),
                contentDescription = null,
                tint = Color(ZCDSColorUtils.getPrimaryColor()),
                modifier = Modifier.clickable(onClick = onInfoButtonClick)
            )

        }
        conversations.forEach { conversation ->
            ZEMTDropdownGroup(
                groupTitle = conversation.name,
                groupIcon = conversation.icon,
                isOpenable = false,
                onGroupClick = { onConversationClick(conversation) },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ZEMTConversationsViewPreview() {
    ZEMTConversationsView(conversations = ZEMTMockConversationList2())
}