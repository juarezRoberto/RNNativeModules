package com.upax.zemytalents.ui.conversations.makeconversation

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.data.mock.ZEMTMockConversationList
import com.upax.zemytalents.data.mock.ZEMTMockPhraseList
import com.upax.zemytalents.ui.conversations.makeconversation.composables.ZEMTChooseConversationView
import com.upax.zemytalents.ui.conversations.makeconversation.composables.ZEMTFinishConversation
import com.upax.zemytalents.ui.conversations.makeconversation.composables.ZEMTNumberedProgressBar
import com.upax.zemytalents.ui.conversations.makeconversation.composables.ZEMTShowPhrase
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTConversationData
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationCaptions
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationProgress
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationStep
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTPhraseData
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTOutlinedButton
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem
import com.upax.zcdesignsystem.R as DesignSystem

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ZEMTMakeConversationView(
    currentStep: ZEMTMakeConversationStep,
    makeConversationData: ZEMTMakeConversationProgress,
    conversationList: List<ZEMTCheckGroupItem>,
    phraseList: List<ZEMTCheckGroupItem>,
    captions: ZEMTMakeConversationCaptions,
    modifier: Modifier = Modifier,
    nextStep: () -> Unit = {},
    prevStep: () -> Unit = {},
    onPhraseSelected: (ZEMTPhraseData) -> Unit = {},
    onConversationSelected: (ZEMTConversationData) -> Unit = {},
    onConversationRealized: (Boolean) -> Unit = {},
    onCommentChanged: (String) -> Unit = {},
) {
    val stepList = ZEMTMakeConversationStep.entries
    val continueButtonEnabled = when (currentStep) {
        ZEMTMakeConversationStep.CHOOSE_CONVERSATION -> makeConversationData.conversationId.isNotEmpty()
        ZEMTMakeConversationStep.CHOOSE_PHRASE -> makeConversationData.phraseId.isNotEmpty()
        ZEMTMakeConversationStep.SHOW_PHRASE -> true
        ZEMTMakeConversationStep.SUMMARY ->
            makeConversationData.isConversationMade != null && makeConversationData.comment.isNotEmpty()

    }

    val pagerState = rememberPagerState(pageCount = { stepList.size })

    val onItemSelected: (Int) -> Unit = { index ->
        if (currentStep.isChooseConversation())
            onConversationSelected(conversationList[index].toConversationData())
        if (currentStep.isChoosePhrase())
            onPhraseSelected(phraseList[index].toPhraseData())
    }

    LaunchedEffect(currentStep) {
        pagerState.animateScrollToPage(currentStep.stepToIndex())
    }

    Column(modifier = modifier) {
        ZEMTNumberedProgressBar(
            currentIndex = currentStep.stepToIndex(),
            numbers = stepList.map { step -> step.stepToIndex() + 1 },
            isSelected = continueButtonEnabled,
            currentColor = colorResource(DesignSystem.color.zcds_success),
            unselectedColor = colorResource(DesignSystem.color.zcds_light_gray_300),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            verticalAlignment = Alignment.Top
        ) { page ->
            var title = ""
            var subtitle = ""
            var optionList = emptyList<ZEMTCheckGroupItem>()

            val scrollPageStep = ZEMTMakeConversationStep.indexToStep(page)
            when (scrollPageStep) {
                ZEMTMakeConversationStep.CHOOSE_CONVERSATION -> {
                    title = stringResource(R.string.zemt_conversations_choose)
                    subtitle = stringResource(R.string.zemt_conversations_time_to_start)
                    optionList = conversationList
                }

                ZEMTMakeConversationStep.CHOOSE_PHRASE -> {
                    title = stringResource(R.string.zemt_phrase_choose)
                    subtitle = captions.step2Caption
                    optionList = phraseList
                }

                ZEMTMakeConversationStep.SHOW_PHRASE -> {
                    title = stringResource(R.string.zemt_conversations_make_conversation_title)
                    subtitle = stringResource(R.string.zemt_conversations_use_phrase)
                    optionList = emptyList()
                }

                ZEMTMakeConversationStep.SUMMARY -> {
                    title = stringResource(R.string.zemt_conversations_finish_conversation)
                    subtitle = captions.step4Caption
                    optionList = emptyList()
                }
            }

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                ZEMTChooseConversationView(
                    onItemSelected = onItemSelected,
                    titleString = title,
                    subtitleScreen = subtitle,
                    optionList = optionList,
                    selectedIndex =
                    if (scrollPageStep.isChooseConversation()) getSelectedItemIndex(
                        makeConversationData.conversationId,
                        conversationList
                    )
                    else if (scrollPageStep.isChoosePhrase()) getSelectedItemIndex(
                        makeConversationData.phraseId,
                        phraseList
                    )
                    else -1
                )

                if (scrollPageStep.isShowPhrase()) {
                    ZEMTShowPhrase(
                        phrase = phraseList.find { it.id == makeConversationData.phraseId }?.text.orEmpty(),
                        modifier = Modifier
                            .padding(top = 54.dp)
                            .padding(horizontal = 8.dp)
                    )
                } else if (scrollPageStep.isSummary()) {
                    ZEMTFinishConversation(
                        modifier = Modifier.padding(top = 12.dp),
                        phrase = phraseList.find { it.id == makeConversationData.phraseId }?.text.orEmpty(),
                        conversationRealized = onConversationRealized,
                        onCommentChanged = onCommentChanged,
                        comment = makeConversationData.comment,
                        isConversationRealized = makeConversationData.isConversationMade
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f, true))
        val chooseConversationContinueButtonVisible = currentStep.isChooseConversation()

        Box {

            androidx.compose.animation.AnimatedVisibility(
                visible = !chooseConversationContinueButtonVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = fadeOut(animationSpec = tween(durationMillis = 500))
            ) {
                Row {
                    ZEMTOutlinedButton(
                        text = stringResource(R.string.zemt_go_back),
                        modifier = Modifier
                            .weight(.5f, true)
                            .padding(bottom = 8.dp, end = 16.dp),
                        onClick = prevStep,
                    )
                    ZEMTButton(
                        text = stringResource(if (currentStep.isSummary()) R.string.zemt_finish else R.string.zemt_continue),
                        modifier = Modifier
                            .weight(.5f, true)
                            .padding(bottom = 8.dp),
                        onClick = nextStep,
                        enabled = continueButtonEnabled
                    )
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = chooseConversationContinueButtonVisible,
                enter = fadeIn() + expandHorizontally(animationSpec = tween(durationMillis = 500)) { fullWidth -> fullWidth / 2 },
                exit = fadeOut(animationSpec = tween(durationMillis = 700)) + shrinkHorizontally(
                    animationSpec = tween(durationMillis = 500)
                ) { fullWidth -> fullWidth / 2 }
            ) {
                ZEMTButton(
                    text = stringResource(R.string.zemt_continue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    onClick = nextStep,
                    enabled = continueButtonEnabled || makeConversationData.conversationId.isNotEmpty()
                )
            }
        }

    }
}

private fun getSelectedItemIndex(selectedId: String, list: List<ZEMTCheckGroupItem>): Int {
    val foundItem = list.find { it.id == selectedId }
    return if (foundItem != null) list.indexOf(foundItem) else -1
}

@Preview
@Composable
private fun ZEMTMakeConversationViewPreview() {
    ZEMTMakeConversationView(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding(),
        makeConversationData = ZEMTMakeConversationProgress(),
        currentStep = ZEMTMakeConversationStep.SUMMARY,
        conversationList = ZEMTMockConversationList(),
        phraseList = ZEMTMockPhraseList(),
        captions = ZEMTMakeConversationCaptions()
    )
}