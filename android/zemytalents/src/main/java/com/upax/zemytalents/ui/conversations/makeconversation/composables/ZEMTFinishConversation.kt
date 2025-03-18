package com.upax.zemytalents.ui.conversations.makeconversation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.data.mock.ZEMTMockPhraseList
import com.upax.zemytalents.data.mock.ZEMTMockYesNoList
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTFinishConversation(
    phrase: String,
    comment: String,
    modifier: Modifier = Modifier,
    isConversationRealized: Boolean? = null,
    conversationRealized: (Boolean) -> Unit,
    onCommentChanged: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        ZEMTRoundedBox {
            ZEMTText(
                text = stringResource(R.string.zemt_conversations_used_phrase),
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMediumBlack,
                color = colorResource(DesignSystem.color.zcds_very_dark_gray_700).toArgb()
            )
            ZEMTText(
                text = "\"$phrase\"",
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
                color = colorResource(DesignSystem.color.zcds_very_dark_gray_700).toArgb()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        val list = ZEMTMockYesNoList().map { it.copy(selected = (isConversationRealized == true && it.id == "0"  || isConversationRealized == false && it.id == "1")) }
        ZEMTFormSpinner(
            hint = stringResource(R.string.zemt_conversations_make_conversation_is_done),
            optionList = list,
            onOptionSelected = { data ->
                conversationRealized(data.value)
            },
        )
        Spacer(modifier = Modifier.height(8.dp))

        val characterLimit = 1000
        var value by remember { mutableStateOf(comment) }
        ZEMTTextArea(
            value = value,
            onValueChange = {
                onCommentChanged(it)
                value = it
            },
            label = stringResource(R.string.zemt_conversations_mandatory_comments),
            modifier = Modifier.padding(),
            supportingText = {
                val textColor = colorResource(DesignSystem.color.zcds_mid_gray_500).toArgb()
                val style = DesignSystem.style.TextAppearance_ZCDSApp_BodySmall
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .padding(horizontal = 4.dp),
                ) {
                    ZEMTText(
                        text = "Character limit reached",
                        modifier = Modifier
                            .weight(1f, true),
                        color = textColor,
                        style = style
                    )
                    ZEMTText(
                        text = "${value.length}/$characterLimit",
                        modifier = Modifier.padding(start = 8.dp),
                        color = textColor,
                        style = style
                    )
                }
            },
            characterLimit = characterLimit
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ZEMTFinishConversationPreview() {
    ZEMTFinishConversation(
        phrase = ZEMTMockPhraseList().first().text,
        modifier = Modifier.padding(horizontal = 16.dp),
        conversationRealized = {},
        isConversationRealized = true,
        comment = "asdasdas"
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ZEMTFinishConversationPreview2() {
    ZEMTFinishConversation(
        phrase = ZEMTMockPhraseList().first().text,
        modifier = Modifier.padding(horizontal = 16.dp),
        conversationRealized = {},
        isConversationRealized = false,
        comment = "asdasdas"
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ZEMTFinishConversationPreview3() {
    ZEMTFinishConversation(
        phrase = ZEMTMockPhraseList().first().text,
        modifier = Modifier.padding(horizontal = 16.dp),
        conversationRealized = {},
        isConversationRealized = null,
        comment = "asdasdas"
    )
}