package com.upax.zemytalents.ui.conversations.makeconversation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upax.zcdesignsystem.R

@Composable
fun ZEMTRoundedBox(
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val defaultModifier = Modifier
        .background(
            color = colorResource(id = R.color.zcds_extra_light_gray_100),
            shape = CardDefaults.shape
        )
        .padding(vertical = verticalPadding, horizontal = horizontalPadding)
    Column(
        modifier = modifier.then(defaultModifier)
    ) {
        content()
    }
}