package com.upax.zemytalents.ui.shared.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar

@Composable
internal fun ZEMTTopAppBar(
    title: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String = "",
    titleStyle: ZCDSTopAppBar.TitlesStyle = ZCDSTopAppBar.TitlesStyle.Small,
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            ZCDSTopAppBar(context = context).apply {
                enableTopAppBarThemeColor(true)
                setTitlesStyle(titleStyle)
            }
        },
        update = { topAppBar ->
            topAppBar.setTopAppBarBackgroundColor(ZCDSColorUtils.getPrimaryColor())
            topAppBar.setTitle(title)
            topAppBar.setNavigationClickListener { onBackPressed() }
            if (subtitle.isNotEmpty()) topAppBar.setSubtitle(subtitle)
        })
}

@Preview
@Composable
private fun ZEMTTopAppbarPreview() {
    ZEMTTopAppBar(
        title = "Mis Talentos",
        subtitle = "Módulo 1 Descubre",
        onBackPressed = {}
    )
}