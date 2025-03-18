package com.upax.zemytalents.ui.shared.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.data.mock.ZEMTMockConversationList
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem

/**
 * Only one item can be selected at a time
 * @param onItemSelected Callback to notify when an item is selected and pass the index of the item
 * @param groupList List of items to be displayed of type [ZEMTCheckGroupItem]
 * */
@Composable
internal fun ZEMTCheckGroup(
    groupList: List<ZEMTCheckGroupItem>,
    modifier: Modifier = Modifier,
    onItemSelected: (Int) -> Unit = {},
    selectedIndex: Int = -1
) {
    Column(modifier = modifier) {
        groupList.forEachIndexed { index, item ->
            ZEMTCheckItem(
                itemText = item.text,
                itemIcon = item.icon,
                isChecked = index == selectedIndex,
                modifier = Modifier.padding(bottom = 12.dp),
                onItemClick = {
                    onItemSelected(index)
                }
            )
        }
    }

}

@Preview
@Composable
private fun ZEMTCheckGroupPreview() {
    ZEMTCheckGroup(groupList = ZEMTMockConversationList())
}