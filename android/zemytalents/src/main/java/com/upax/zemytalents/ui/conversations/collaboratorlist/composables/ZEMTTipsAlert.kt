package com.upax.zemytalents.ui.conversations.collaboratorlist.composables

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zccommon.extensions.EMPTY
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.shared.composables.ZEMTTextButton
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
internal fun ZEMTTipsAlert(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    tipsList: List<ZEMTTipsAlertItem> = ZEMTTipsAlertItemMock.getCollaboratorsCarrouselSlides(
        LocalContext.current,
        slide2 = String.EMPTY,
        slide4 = String.EMPTY
    )
) {
    val iconSize = 120.dp
    val boxWidth = 300.dp
    val boxHeight = 236.dp

    var showLottie by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
        ) {
            tipsList.forEach { tipItem ->
                showLottie = tipItem.showLottie
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Card(
                        modifier = Modifier
                            .width(boxWidth)
                            .height(boxHeight),
                        colors = CardDefaults.cardColors()
                            .copy(containerColor = colorResource(DesignSystem.color.zcds_white))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(88.dp))
                            if (tipItem.title.isNotEmpty()) ZEMTText(
                                text = tipItem.title,
                                style = DesignSystem.style.TextAppearance_ZCDSApp_Header04,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )
                            ZEMTText(
                                text = tipItem.description,
                                textAlign = TextAlign.Center,
                                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge
                            )
                            Spacer(modifier = Modifier.weight(1f, true))
                            ZEMTTextButton(
                                text = stringResource(R.string.zemt_tip_exit),
                                onClick = onDismissRequest,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                            )
                        }
                    }
                    if (!showLottie) return@Box
                    val lottie by rememberLottieComposition(
                        LottieCompositionSpec.Asset(
                            ZCDSLottieCatalog.TipsZeus.filename
                        )
                    )
                    LottieAnimation(
                        composition = lottie,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(iconSize)
                            .offset(y = -boxHeight / 2)
                    )
                }
            }
        }
    }
}

internal data class ZEMTTipsAlertItem(
    val title: String = "",
    val description: CharSequence = "",
    val showLottie: Boolean = false
)

internal object ZEMTTipsAlertItemMock {
    fun getCollaboratorsCarrouselSlides(context: Context, slide2: String, slide4: String) = listOf(
        ZEMTTipsAlertItem(
            title = context.getString(R.string.zemt_tip_title),
            description = context.getText(R.string.zemt_tip_question),
            showLottie = true
        ),
        ZEMTTipsAlertItem(description = slide2),
        ZEMTTipsAlertItem(description = context.getString(R.string.zemt_tip_2)),
        ZEMTTipsAlertItem(description = slide4),
    )

    fun getTalentResumeTips(context: Context, tipDescription: String) = listOf(
        ZEMTTipsAlertItem(
            title = context.getString(R.string.zemt_tip_title),
            description = context.getText(R.string.zemt_tip2_info),
            showLottie = true
        ),
        ZEMTTipsAlertItem(description = tipDescription)
    )
}

@Preview
@Composable
private fun ZEMTTipsAlertPreview() {
    ZEMTTipsAlert(onDismissRequest = {})
}