package com.upax.zemytalents.data.mock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.upax.zemytalents.R
import com.upax.zemytalents.data.local.icons.ZEMTIconParser
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem

@Composable
internal fun ZEMTMockConversationList() = listOf(
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_conversation_record_item0),
        ZEMTIconParser.FIJAR_OBJECTIVOS.iconId,
        "0"
    ),
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_conversation_record_item1),
        ZEMTIconParser.CONEXION_RAPIDA.iconId,
        "1"
    ),
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_conversation_record_item2),
        ZEMTIconParser.CHEQUEO.iconId,
        "2"
    ),
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_conversation_record_item3),
        ZEMTIconParser.DESEMPENO.iconId,
        "3"
    ),
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_conversation_record_item4),
        ZEMTIconParser.DESARROLLO.iconId,
        "4"
    ),
)

@Composable
internal fun ZEMTMockConversationList2() = listOf(
    ZEMTConversation(
        conversationId = "1",
        name = "Conversation 1",
        description = "Description 1",
        order = 1,
        icon = ZEMTIconParser.FIJAR_OBJECTIVOS.iconId,
        lottieUrl = ""
    ),
    ZEMTConversation(
        conversationId = "2",
        name = "Conversation 2",
        description = "Description 2",
        order = 2,
        icon = ZEMTIconParser.CONEXION_RAPIDA.iconId,
        lottieUrl = ""
    ),
    ZEMTConversation(
        conversationId = "3",
        name = "Conversation 3",
        description = "Description 3",
        order = 3,
        icon = ZEMTIconParser.CHEQUEO.iconId,
        lottieUrl = ""
    ),
    ZEMTConversation(
        conversationId = "4",
        name = "Conversation 4",
        description = "Description 4",
        order = 4,
        icon = ZEMTIconParser.DESEMPENO.iconId,
        lottieUrl = ""
    ),
    ZEMTConversation(
        conversationId = "5",
        name = "Conversation 5",
        description = "Description 5",
        order = 5,
        icon = ZEMTIconParser.DESARROLLO.iconId,
        lottieUrl = ""
    ),
)
