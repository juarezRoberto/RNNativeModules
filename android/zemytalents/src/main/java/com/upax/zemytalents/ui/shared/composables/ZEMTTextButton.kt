package com.upax.zemytalents.ui.shared.composables

import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.upax.zcdesignsystem.R
import com.upax.zcdesignsystem.widget.button.ZCDSTextButton

@Composable
internal fun ZEMTTextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {

    AndroidView(
        factory = { context ->
            val materialContext = ContextThemeWrapper(context, R.style.Theme_ZCDSAppTheme)
            ZCDSTextButton(materialContext).apply {
                setText(text)
                setOnClickListener { onClick() }
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun ZEMTTextButtonPreview() {
    ZEMTTextButton("text button", onClick = {})
}