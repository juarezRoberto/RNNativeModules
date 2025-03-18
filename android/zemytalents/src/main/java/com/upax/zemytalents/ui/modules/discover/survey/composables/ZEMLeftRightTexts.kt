package com.upax.zemytalents.ui.modules.discover.survey.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upax.zemytalents.ui.shared.composables.ZEMTText

@Composable
fun ZEMTLeftRightTexts(
    leftText: String,
    rightText: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {

        val textModifier = Modifier
            .weight(1f, true)
            .padding(horizontal = 24.dp)

        ZEMTText(
            text = leftText,
            modifier = textModifier.testTag("left text")
                .semantics { this.text = AnnotatedString(leftText) },
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )

        ZEMTText(
            text = rightText,
            modifier = textModifier.testTag("right text")
                .semantics { this.text = AnnotatedString(rightText) },
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    device = Devices.PIXEL_7,
    showSystemUi = true
)
@Composable
fun ZEMTLeftRightTextsPreview() {
    ZEMTLeftRightTexts(
        leftText = "Los demás acuden a mi para verificar que su información sea exacta, bien documentada",
        rightText = "Me siento triste cuando no le caigo bien a alguien",
        modifier = Modifier.padding(vertical = 16.dp)
    )
}