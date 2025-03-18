package com.upax.zemytalents.ui.advice.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.advice.model.ZEMTAdviceItemModel

@Composable
fun ZEMTAdviceGrid(adviceList: List<ZEMTAdviceItemModel>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        adviceList.forEach { adviceItem ->
            ZEMTAdviceItem(adviceItem, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp))
        }
    }
}

@Preview
@Composable
private fun ZEMTAdviceGridPreview() {
    val adviceItem = ZEMTAdviceItemModel(
        resourceId = R.drawable.zemt_ic_wifi,
        description = stringResource(R.string.zemt_advice_wifi)
    )
    val adviceList = (0..5).map { adviceItem }
    ZEMTAdviceGrid(adviceList, modifier = Modifier.fillMaxWidth())
}