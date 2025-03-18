package com.upax.zemytalents.ui.modules.apply.survey

import androidx.annotation.RawRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.apply.survey.mock.ZEMTMockApplySurveyData
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel.Status
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyQuestionUiModel
import com.upax.zemytalents.ui.modules.shared.composables.ZEMTSurveyTalents
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTSurveyApplyScreen(
    uiState: ZEMTSurveyApplyUiState,
    modifier: Modifier = Modifier,
    onAnswerStatusChange: (ZEMTSurveyApplyAnswerOptionUiModel, Status) -> Unit
) {
    Column(
        modifier = modifier
            .background(colorResource(id = RDS.color.zcds_extra_light_gray_100))
            .navigationBarsPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        ZEMTSurveyTalents(
            talents = uiState.surveyTalents,
            modifier = Modifier.padding(vertical = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
        )

        uiState.currentQuestion?.let {
            ZEMTSurveyApplyQuestion(
                question = it,
                onAnswerStatusChange = onAnswerStatusChange)
        }

        uiState.currentTalent?.let {
            val lottie by rememberLottieComposition(
                LottieCompositionSpec.Url(uiState.currentTalent.lottieUrl)
            )
            LottieAnimation(
                composition = lottie,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .aspectRatio(1.7f)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun ZEMTSurveyApplyQuestion(
    question: ZEMTSurveyApplyQuestionUiModel,
    modifier: Modifier = Modifier,
    onAnswerStatusChange: (ZEMTSurveyApplyAnswerOptionUiModel, Status) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
            .padding(top = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
    ) {
        ZEMTText(
            text = question.text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = RDS.style.TextAppearance_ZCDSApp_Header03
        )

        question.currentAnswer?.let { answer ->
            ZEMTAnswer(answer, onAnswerStatusChange)
        }
    }
}

@Composable
private fun ZEMTAnswer(
    answer: ZEMTSurveyApplyAnswerOptionUiModel,
    onAnswerStatusChange: (ZEMTSurveyApplyAnswerOptionUiModel, Status) -> Unit,
    modifier: Modifier = Modifier
) {

    var statusAnswer by remember { mutableStateOf(answer.status) }

    LaunchedEffect(answer) {
        statusAnswer = answer.status
    }

    val strokeColor = when (statusAnswer) {
        Status.UNCHECKED -> null
        Status.CHECK_NEGATIVE -> {
            colorResource(id = RDS.color.zcds_error)
        }

        Status.CHECK_POSITIVE -> {
            colorResource(id = RDS.color.zcds_success)
        }
    }

    Card(
        modifier = modifier
            .aspectRatio(4 / 3f)
            .fillMaxSize()
            .padding(top = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_xxlarge))
            .padding(bottom = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
            .padding(horizontal = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_medium)),
        colors = CardDefaults.cardColors()
            .copy(containerColor = colorResource(id = RDS.color.zcds_white)),
        elevation = CardDefaults.cardElevation(3.dp),
        shape = RoundedCornerShape(dimensionResource(id = RDS.dimen.zcds_elevation_large)),
        border = strokeColor?.let { BorderStroke(1.dp, it) }
    ) {

        when (statusAnswer) {
            Status.UNCHECKED -> {
                ZEMTAnswerText(
                    answer.text,
                    onNegative = { statusAnswer = Status.CHECK_NEGATIVE },
                    onPositive = { statusAnswer = Status.CHECK_POSITIVE }
                )
            }

            Status.CHECK_NEGATIVE -> {
                ZEMTAnswerLottie(
                    lottieRes = R.raw.zemt_negative_apply,
                    onAnimationEnd = { onAnswerStatusChange.invoke(answer, Status.CHECK_NEGATIVE) }
                )
            }

            Status.CHECK_POSITIVE -> {
                ZEMTAnswerLottie(
                    lottieRes = R.raw.zemt_positive_apply,
                    onAnimationEnd = { onAnswerStatusChange.invoke(answer, Status.CHECK_POSITIVE) }
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.ZEMTAnswerText(
    text: String,
    onNegative: () -> Unit,
    onPositive: () -> Unit
) {
    ZEMTText(
        text = text,
        modifier = Modifier
            .padding(dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
            .weight(1f, fill = true)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = RDS.style.TextAppearance_ZCDSApp_BodyLarge
    )
    ZEMTSurveyQuestionResponseIcons(
        onNegative = onNegative,
        onPositive = onPositive,
        modifier = Modifier
            .padding(bottom = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
            .padding(horizontal = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
    )
}

@Composable
private fun ColumnScope.ZEMTAnswerLottie(@RawRes lottieRes: Int, onAnimationEnd: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(lottieRes)
    )
    val animationState = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        composition?.let {
            animationState.animate(
                speed = 3f, // FIXME: adjust speed
                composition = it,
                iterations = 1,
                clipSpec = LottieClipSpec.Progress(min = 0f, max = 0.5f)
            )
            onAnimationEnd()
        }
    }

    LottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = Modifier
            .weight(1f, fill = true)
            .align(Alignment.CenterHorizontally)
            .size(100.dp)
    )
}

@Composable
private fun ZEMTSurveyQuestionResponseIcons(
    onNegative: () -> Unit,
    onPositive: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = { onNegative.invoke() },
            modifier = Modifier
                .testTag("negative button")
                .background(color = colorResource(id = RDS.color.zcds_error), shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = RDS.drawable.zcds_ic_x_solid),
                tint = colorResource(id = RDS.color.zcds_white),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        IconButton(
            onClick = { onPositive.invoke() },
            modifier = Modifier
                .testTag("positive button")
                .background(color = colorResource(id = RDS.color.zcds_success), shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = RDS.drawable.zcds_ic_check_solid),
                tint = colorResource(id = RDS.color.zcds_white),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ZEMTSurveyApplyScreenPreview() {
    ZEMTSurveyApplyScreen(
        uiState = ZEMTSurveyApplyUiState(
            surveyTalents = ZEMTMockApplySurveyData.getTalents(),
        ),
        onAnswerStatusChange = { _, _ -> }
    )
}