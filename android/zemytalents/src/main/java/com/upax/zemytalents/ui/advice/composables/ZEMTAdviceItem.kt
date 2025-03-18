package com.upax.zemytalents.ui.advice.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.advice.model.ZEMTAdviceItemModel
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
fun ZEMTAdviceItem(adviceItem: ZEMTAdviceItemModel, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = adviceItem.resourceId), contentDescription = null)
        ZEMTText(
            text = adviceItem.description,
            style = RDS.style.TextAppearance_ZCDSApp_BodyMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview
@Composable
private fun ZEMTAdviceItemPreview() {
    val modifier = Modifier
        .fillMaxWidth()
    val adviceItem = ZEMTAdviceItemModel(
        resourceId = R.drawable.zemt_ic_wifi,
        description = stringResource(R.string.zemt_advice_wifi)
    )
    ZEMTAdviceItem(adviceItem = adviceItem, modifier = modifier)
}