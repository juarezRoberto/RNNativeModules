package com.upax.zemytalents.ui.modules.confirm.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.confirm.mock.ZEMTMockConfirmSurveyData
import com.upax.zemytalents.ui.modules.confirm.survey.composables.ZEMTCollapsableQuestionContainer
import com.upax.zemytalents.ui.modules.confirm.survey.composables.ZEMTCollapsableQuestionContent
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.shared.composables.ZEMTSurveyTalents
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTSurveyConfirmScreen(
    modifier: Modifier = Modifier,
    onSaveQuestionAnswer: (
        questionId: Int, options: List<ZEMTSurveyConfirmAnswerOptionUiModel>
    ) -> Unit,
    onHideIntroduction: () -> Unit,
    uiState: ZEMTSurveyConfirmUiState,
) {

    Box(
        modifier = modifier
            .background(colorResource(id = RDS.color.zcds_extra_light_gray_100))
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        Column {
            ZEMTSurveyTalents(
                talents = uiState.talents,
                modifier = Modifier.padding(vertical = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
            )

            if (uiState.showTalentIntroduction) {
                val lottie by rememberLottieComposition(LottieCompositionSpec.Url(uiState.currentTalent?.lottieUrl.orEmpty()))
                LottieAnimation(
                    composition = lottie,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                )
                ZEMTText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_xxlarge)),
                    text = stringResource(
                        R.string.zemt_you_have_above_talent,
                        uiState.currentTalent?.name.orEmpty()
                    ),
                    textAlign = TextAlign.Center,
                    style = RDS.style.TextAppearance_ZCDSApp_Header03
                )
                ZEMTText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_small)),
                    text = stringResource(R.string.zemt_solve_the_puzzles_hidden_for_you),
                    textAlign = TextAlign.Center,
                    style = RDS.style.TextAppearance_ZCDSApp_BodyLarge
                )
                ZEMTButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(RDS.dimen.zcds_margin_padding_size_large))
                        .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_xxxlarge)),
                    text = stringResource(R.string.zemt_start),
                    onClick = { onHideIntroduction.invoke() }
                )
            } else {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
                ) {
                    ZEMTText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_xxlarge)),
                        text = stringResource(R.string.zemt_these_are_the_questions),
                        textAlign = TextAlign.Center,
                        style = RDS.style.TextAppearance_ZCDSApp_Header03
                    )
                    ZEMTText(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(RDS.dimen.zcds_margin_padding_size_small)),
                        text = stringResource(R.string.zemt_remember_there_are_no_wrog_or_correct_answers),
                        textAlign = TextAlign.Center,
                        style = RDS.style.TextAppearance_ZCDSApp_BodyLarge
                    )
                    uiState.currentTalent?.questions?.forEach { question ->
                        ZEMTCollapsableQuestionContainer(
                            modifier = Modifier.padding(vertical = 8.dp),
                            title = question.header,
                            isOpen = uiState.openQuestionId == question.id,
                            isCompleted = question.isCompleted
                        ) {
                            ZEMTCollapsableQuestionContent(
                                title = question.text,
                                answerOptionList = question.answerOptions,
                                onCompleted = { options ->
                                    onSaveQuestionAnswer(
                                        question.id,
                                        options
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmSurveyScreenPreview() {
    ZEMTSurveyConfirmScreen(
        uiState = ZEMTSurveyConfirmUiState(
            talents = ZEMTMockConfirmSurveyData.getTalents()
        ),
        onSaveQuestionAnswer = { id, options -> },
        onHideIntroduction = {}
    )
}