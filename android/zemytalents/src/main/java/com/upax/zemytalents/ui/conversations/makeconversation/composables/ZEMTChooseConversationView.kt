package com.upax.zemytalents.ui.conversations.makeconversation.composables

import android.graphics.Typeface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.data.mock.ZEMTMockConversationList
import com.upax.zemytalents.ui.shared.composables.ZEMTCheckGroup
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
internal fun ZEMTChooseConversationView(
    titleString: String,
    subtitleScreen: String,
    modifier: Modifier = Modifier,
    optionList: List<ZEMTCheckGroupItem> = emptyList(),
    onItemSelected: (Int) -> Unit = {},
    selectedIndex: Int = -1
) {
    Column(modifier = modifier) {
        ZEMTText(
            text = titleString,
            style = DesignSystem.style.TextAppearance_ZCDSApp_BodyXLarge,
            modifier = Modifier.padding(top = 16.dp),
            textStyle = Typeface.BOLD,
            color = colorResource(DesignSystem.color.zcds_very_dark_gray_700).toArgb()
        )
        ZEMTText(
            subtitleScreen,
            style = com.upax.zcdesignsystem.R.style.TextAppearance_ZCDSApp_BodyMedium,
            modifier = Modifier.padding(top = 12.dp),
            color = colorResource(DesignSystem.color.zcds_dark_gray_600).toArgb()
        )
        ZEMTCheckGroup(
            groupList = optionList,
            onItemSelected = onItemSelected,
            modifier = Modifier.padding(top = 24.dp),
            selectedIndex = selectedIndex
        )
    }
}

@Preview
@Composable
private fun ZEMTChooseConversationViewPreview() {
    ZEMTChooseConversationView(
        titleString = stringResource(R.string.zemt_conversations_choose),
        subtitleScreen = stringResource(R.string.zemt_conversations_time_to_start),
        optionList = ZEMTMockConversationList()
    )
}