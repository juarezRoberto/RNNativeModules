package com.upax.zemytalents.ui.conversations.conversationhistory

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.conversations.conversationhistory.model.ZEMTConversationMock
import com.upax.zemytalents.ui.conversations.conversationhistory.model.ZEMTConversationState
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTConversationView(conversationList: List<ZEMTConversationState>, modifier: Modifier = Modifier) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(color = colorResource(id = DesignSystem.color.zcds_extra_light_gray_100))
    ) {
        itemsIndexed(conversationList) { index, conversation ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = if (index == conversationList.lastIndex) 24.dp else 0.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors()
                    .copy(containerColor = colorResource(id = DesignSystem.color.zcds_white))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    when (conversation) {
                        is ZEMTConversationState.Uncompleted -> {
                            val padding = Modifier.padding(top = 16.dp)
                            ZEMTText(
                                text = conversation.date,
                                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyXLarge,
                                textStyle = Typeface.BOLD,
                                color = colorResource(id = DesignSystem.color.zcds_mid_gray_500).toArgb()
                            )
                            ZEMTText(
                                text = conversation.title,
                                modifier = padding,
                                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge,
                                textStyle = Typeface.BOLD,
                                color = colorResource(id = DesignSystem.color.zcds_error).toArgb()
                            )
                            ZEMTText(
                                text = conversation.message,
                                modifier = padding.padding(top = 16.dp),
                                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
                                textStyle = Typeface.ITALIC,
                                color = colorResource(id = DesignSystem.color.zcds_mid_gray_500).toArgb()
                            )

                        }

                        is ZEMTConversationState.Completed -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                ZEMTText(
                                    text = conversation.date,
                                    style = DesignSystem.style.TextAppearance_ZCDSApp_BodyXLarge,
                                    textStyle = Typeface.BOLD,
                                    color = colorResource(id = DesignSystem.color.zcds_mid_gray_500).toArgb(),
                                    modifier = Modifier.weight(1f, true)
                                )

                                Icon(
                                    painter = painterResource(id = DesignSystem.drawable.zcds_ic_check_solid),
                                    contentDescription = null,
                                    tint = colorResource(id = DesignSystem.color.zcds_success)
                                )
                            }

                            if (conversation.phrase.isNotEmpty()) ZEMTText(
                                text = conversation.phrase,
                                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
                                textStyle = Typeface.ITALIC,
                                modifier = Modifier.padding(top = 20.dp),
                            )

                            ZEMTText(
                                text = stringResource(R.string.zemt_comments),
                                modifier = Modifier.padding(top = 20.dp),
                                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
                                color = colorResource(id = DesignSystem.color.zcds_mid_gray_500).toArgb()
                            )
                            ZEMTText(
                                text = conversation.comment,
                                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge,
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun ZEMTConversationViewPreview() {
    ZEMTConversationView(conversationList = ZEMTConversationMock.getConversationList())
}