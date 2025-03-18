package com.upax.zemytalents.ui.shared.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun ZEMTSimpleButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Column {
        Button(onClick = onClick) {
            Text(text = text)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
internal fun ZEMTSimpleButtonPreview() {
    ZEMTSimpleButton(text = "Next") {

    }
}