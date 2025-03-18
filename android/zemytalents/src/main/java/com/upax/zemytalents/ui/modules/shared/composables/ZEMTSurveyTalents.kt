package com.upax.zemytalents.ui.modules.shared.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zemytalents.ui.modules.confirm.mock.ZEMTMockConfirmSurveyData
import com.upax.zemytalents.ui.modules.shared.model.ZEMTTalentUiModel
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTSurveyTalents(
    talents: List<ZEMTTalentUiModel>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    val indexTalentSelected = talents.indexOfFirst { it.selected }

    LaunchedEffect(indexTalentSelected) {
        if (indexTalentSelected == -1) return@LaunchedEffect
        val firstVisibleItemIndex = scrollState.firstVisibleItemIndex
        val visibleItemsCount = scrollState.layoutInfo.visibleItemsInfo.size

        val isItemCompletelyOutOfView =
            indexTalentSelected < firstVisibleItemIndex ||
                    indexTalentSelected >= firstVisibleItemIndex + visibleItemsCount

        if (isItemCompletelyOutOfView) {
            scrollState.animateScrollToItem(indexTalentSelected)
        }
    }

    LazyRow(
        modifier = modifier.testTag("survey talents"),
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large)
        ),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = RDS.dimen.zcds_margin_padding_size_small)),
        state = scrollState
    ) {
        itemsIndexed(talents, key = { _, item -> item.id }) { index, talent ->
            ZEMTTalent(
                talent = talent,
                modifier = Modifier
                    .testTag(index.toString())
                    .semantics { this.selected = talent.selected }
            )
        }
    }
}

@Composable
private fun ZEMTTalent(
    talent: ZEMTTalentUiModel,
    modifier: Modifier = Modifier
) {

    val cardBorder = if (talent.selected) BorderStroke(
        1.dp, Color(ZCDSColorUtils.getPrimaryColor())
    ) else null

    Card(
        modifier = modifier.then(if (!talent.selected) Modifier.alpha(0.40f) else Modifier),
        colors = CardDefaults.cardColors()
            .copy(containerColor = colorResource(id = RDS.color.zcds_white)),
        shape = RoundedCornerShape(dimensionResource(id = RDS.dimen.zcds_round_corners_large)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = cardBorder
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
                .sizeIn(minWidth = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZEMTTalentIcon(
                primaryIcon = talent.icon,
                secondaryIcon = talent.statusIcon,
                backgroundColor = talent.backgroundColor,
                statusIconColor = talent.statusIconColor,
                colorStroke = talent.colorStroke
            )
            HorizontalDivider(
                modifier = Modifier.padding(dimensionResource(id = RDS.dimen.zcds_margin_padding_size_mini))
            )
            ZEMTText(
                text = talent.name,
                style = RDS.style.TextAppearance_ZCDSApp_BodyMediumBlack,
                modifier = Modifier.testTag(talent.name)
            )
        }
    }
}

@Composable
private fun ZEMTTalentIcon(
    @DrawableRes primaryIcon: Int,
    @DrawableRes secondaryIcon: Int,
    backgroundColor: Color,
    statusIconColor: Color,
    colorStroke: Color
) {

    Box {
        Icon(
            painter = painterResource(id = primaryIcon),
            contentDescription = null,
            modifier = Modifier
                .border(2.dp, colorStroke, CircleShape)
                .padding(4.dp)
                .border(1.dp, Color.White, CircleShape)
                .padding(1.dp)
                .border(2.dp, Color(ZCDSColorUtils.getPrimaryColor()), CircleShape)
                .background(backgroundColor, CircleShape)
                .padding(dimensionResource(id = RDS.dimen.zcds_margin_padding_size_medium))
                .size(40.dp),
            tint = statusIconColor
        )
        Icon(
            painter = painterResource(id = secondaryIcon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(colorStroke, CircleShape)
                .padding(dimensionResource(id = RDS.dimen.zcds_margin_padding_size_mini))
                .size(18.dp),
            tint = colorResource(id = RDS.color.zcds_white)
        )
    }

}

@Composable
@Preview(
    backgroundColor = 0xEAE9EB,
    showBackground = true
)
private fun ZEMTSurveyTalentsPreview() {
    ZEMTSurveyTalents(talents = ZEMTMockConfirmSurveyData.getTalents())
}