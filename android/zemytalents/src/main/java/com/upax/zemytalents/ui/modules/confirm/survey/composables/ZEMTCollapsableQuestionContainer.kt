package com.upax.zemytalents.ui.modules.confirm.survey.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.confirm.mock.ZEMTMockConfirmSurveyData
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

private val iconSize = 24.dp
private val arrowIconSize = 11.dp
private const val rotationAngle = 90f

@Composable
fun ZEMTCollapsableQuestionContainer(
    modifier: Modifier = Modifier,
    title: String,
    isOpen: Boolean = false,
    isCompleted: Boolean = false,
    content: @Composable () -> Unit = {},
) {

    val defaultModifier = Modifier
        .fillMaxWidth()

    val newModifier = modifier.then(defaultModifier)

    Surface(
        modifier = newModifier.then(
            Modifier.clickable(onClick = {})
        ),
        shadowElevation = 4.dp,
        color = Color(LocalContext.current.getColor(RDS.color.zcds_white)),
        shape = RoundedCornerShape(dimensionResource(RDS.dimen.zcds_round_corners_large))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 18.dp)
                .alpha(if (isOpen || isCompleted) 1f else .4f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val icon =
                    if (isCompleted) R.drawable.zemt_ic_done else if (isOpen) R.drawable.zemt_ic_padlock_open_filled else R.drawable.zemt_ic_padlock_closed_filled

                val iconTint =
                    if (isCompleted) RDS.color.zcds_success else RDS.color.zcds_black

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = colorResource(id = iconTint),
                    modifier = Modifier
                        .size(iconSize)
                        .then(if (isCompleted) Modifier.padding(4.dp) else Modifier)
                )

                ZEMTText(
                    text = title,
                    style = RDS.style.TextAppearance_ZCDSApp_Header04,
                    modifier = Modifier.weight(1f, fill = true),
                    color = LocalContext.current.getColor(if (isCompleted && !isOpen) RDS.color.zcds_success else RDS.color.zcds_black)
                )

                Icon(
                    painter = painterResource(id = R.drawable.zemt_ic_keyboard_arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .size(arrowIconSize)
                        .rotate(if (isOpen) rotationAngle else 0f)
                )

            }
            if (isOpen) content()

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTCollapsableQuestionContainerPreview() {
    Column {
        ZEMTCollapsableQuestionContainer(
            title = "Dropdown Title",
            isOpen = true,
            modifier = Modifier.padding(16.dp)
        ) {
            ZEMTCollapsableQuestionContent(
                answerOptionList = ZEMTMockConfirmSurveyData.getOptions(),
//                answeredOptions = listOf(2, 4),
                title = "¿Cómo estableces relaciones fuertes con los demás?",
                onCompleted = {}
            )
        }

        ZEMTCollapsableQuestionContainer(
            title = "Dropdown Title",
            isCompleted = true,
            isOpen = true,

            modifier = Modifier.padding(16.dp)
        ) { Text("Content") }

        ZEMTCollapsableQuestionContainer(
            title = "Dropdown Title",
            isCompleted = true,
            modifier = Modifier.padding(16.dp)
        ) { Text("Content") }

        ZEMTCollapsableQuestionContainer(
            title = "Dropdown Title",
            modifier = Modifier.padding(16.dp)
        ) { Text("Content") }
    }
}