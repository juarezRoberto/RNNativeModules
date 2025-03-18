package com.upax.zemytalents.ui.conversations.collaboratorlist.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.upax.zemytalents.ui.shared.composables.ZEMTSearchView

@Composable
internal fun ZEMTCollaboratorSearcher(
    onClickListener: () -> Unit
) {
    var searchViewHeight by remember { mutableIntStateOf(0) }

    Box {
        ZEMTSearchView(
            isEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .onGloballyPositioned { coordinates ->
                    searchViewHeight = coordinates.size.height
                })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { searchViewHeight.toDp() })
                .clickable { onClickListener.invoke() }
        )
    }
}

@Composable
@Preview
fun ZEMTCollaboratorSearcherPreview() {
    ZEMTCollaboratorSearcher(onClickListener = { })
}