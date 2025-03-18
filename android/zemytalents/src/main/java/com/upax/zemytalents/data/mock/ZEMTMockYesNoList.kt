package com.upax.zemytalents.data.mock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.conversations.makeconversation.composables.ZEMTFormSpinnerOption

@Composable
internal fun ZEMTMockYesNoList(modifier: Modifier = Modifier): List<ZEMTFormSpinnerOption<Boolean>> {
    val optionsArray = stringArrayResource(id = R.array.zemt_yes_no_options)
    return optionsArray.mapIndexed { index, option ->
        ZEMTFormSpinnerOption(id = index.toString(), text = option, value = index == 0, selected = false)
    }
}