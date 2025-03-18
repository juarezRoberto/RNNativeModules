package com.upax.zemytalents.ui.shared.composables

import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.upax.zcdesignsystem.widget.button.ZCDSButton
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val materialContext = ContextThemeWrapper(LocalContext.current, RDS.style.Theme_ZCDSAppTheme)
    AndroidView(
        factory = { _ -> ZCDSButton(context = materialContext) },
        modifier = modifier,
        update = { button ->
            button.text = text
            button.setOnClickListener { onClick() }
            button.isEnabled = enabled
        }
    )
}

@Preview
@Composable
private fun ZEMTButtonPreview() {
    ZEMTButton(text = "Continuar")
}