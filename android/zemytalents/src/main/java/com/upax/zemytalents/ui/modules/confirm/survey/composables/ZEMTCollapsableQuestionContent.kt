package com.upax.zemytalents.ui.modules.confirm.survey.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.confirm.mock.ZEMTMockConfirmSurveyData
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmAnswerOptionUiModel
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTCollapsableQuestionContent(
    modifier: Modifier = Modifier,
    title: String,
    answerOptionList: List<ZEMTSurveyConfirmAnswerOptionUiModel>,
    onCompleted: (List<ZEMTSurveyConfirmAnswerOptionUiModel>) -> Unit
) {
    val defaultModifier = Modifier.padding(top = 24.dp)
    val newModifier = modifier.then(defaultModifier)
    val selectedItems = remember {
        mutableStateListOf(*answerOptionList.map {
            ZMTToggleableItem(
                answerOptionId = it.id,
                checked = it.isChecked,
                text = it.text
            )
        }.toTypedArray())
    }

    Column(modifier = newModifier) {
        ZEMTText(
            text = title,
            style = RDS.style.TextAppearance_ZCDSApp_Header06,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.padding(top = 24.dp))
        selectedItems.forEachIndexed { index, data ->
            ZEMTCheckboxItem(
                text = data.text,
                checked = data.checked,
                onCheckedChange = { checked ->
                    if (checked && data.answerOptionId == answerOptionList.last().id) {
                        for (i in 0 until selectedItems.size - 1) {
                            selectedItems[i] = selectedItems[i].copy(checked = false)
                        }
                    } else if (checked && data.answerOptionId != answerOptionList.last().id) {
                        selectedItems[selectedItems.size - 1] =
                            selectedItems[selectedItems.size - 1].copy(checked = false)
                    }
                    selectedItems[index] = data.copy(checked = checked)
                }
            )
        }
        Spacer(modifier = Modifier.padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_large)))
        ZEMTButton(
            enabled = selectedItems.any { it.checked },
            text = stringResource(R.string.zemt_answer),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val updatedOptions = answerOptionList.map { option ->
                    if (selectedItems
                            .any { it.answerOptionId == option.id && it.checked }) {
                        option.copy(isChecked = true)
                    } else option
                }
                onCompleted(updatedOptions)
//                onCompleted(answerOptionList.filter { answerOption ->
//                    selectedItems
//                        .any { it.answerOptionId == answerOption.id && it.checked }
//                })
            }
        )
    }
}


data class ZMTToggleableItem(
    val answerOptionId: Int,
    val checked: Boolean,
    val text: String,
)

@Preview(showBackground = true)
@Composable
private fun ZEMTCollapsableQuestionContentPreview() {
    Column {
        ZEMTCollapsableQuestionContent(
            answerOptionList = ZEMTMockConfirmSurveyData.getOptions(),
            title = "¿Cómo estableces relaciones fuertes con los demás?",
            onCompleted = {}
        )
        ZEMTCollapsableQuestionContent(
            answerOptionList = ZEMTMockConfirmSurveyData.getOptions(),
            title = "¿Cómo estableces relaciones fuertes con los demás?",
            onCompleted = {}
        )
    }
}