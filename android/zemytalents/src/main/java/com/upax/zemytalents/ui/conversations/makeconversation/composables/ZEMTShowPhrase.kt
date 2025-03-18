package com.upax.zemytalents.ui.conversations.makeconversation.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zcdesignsystem.expose.ZCDSColorVariant
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTShowPhrase(phrase: String, modifier: Modifier = Modifier) {
    ZEMTText(
        text = "\"$phrase\"",
        modifier = modifier,
        style = DesignSystem.style.TextAppearance_ZCDSApp_Header03,
        color = ZCDSColorUtils.getPrimaryColor(colorVariant = ZCDSColorVariant.Dark),
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun ZEMTShowPhrasePreview() {
    ZEMTShowPhrase("Pregúntale que tan importante es para el/ella contar con amigos para toda la vida.", modifier = Modifier.fillMaxHeight())
}