package com.upax.zemytalents.ui.conversations.collaboratorlist

import android.graphics.Typeface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.ui.conversations.collaboratorlist.composables.ZEMTCollaboratorSearcher
import com.upax.zemytalents.ui.conversations.collaboratorlist.composables.ZEMTCollaboratorView
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListCaptions
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListViewType
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorsMockData
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
internal fun ZEMTCollaboratorListView(
    collaboratorList: List<ZEMTCollaboratorInCharge>,
    onCollaboratorClick: (id: String, name: String, profileImageUrl: String) -> Unit,
    captions: ZEMTCollaboratorListCaptions,
    modifier: Modifier = Modifier,
    viewType: ZEMTCollaboratorListViewType = ZEMTCollaboratorListViewType.TALENTS,
    noTalentsCompletedAction: (String, String) -> Unit = { _, _ -> },
    onSearchClick: () -> Unit
) {
    val gridSize = 4
    var title = captions.collaboratorsCaption
    var titleStyle = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium
    var textType = Typeface.NORMAL
    var selectedCollaboratorIndex by remember { mutableIntStateOf(-1) }
    if (viewType == ZEMTCollaboratorListViewType.MAKE_CONVERSATION) {
        title = captions.makeConversationCaption
        titleStyle = DesignSystem.style.TextAppearance_ZCDSApp_BodyXLarge
        textType = Typeface.BOLD
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = 4.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f, true),
            columns = GridCells.Fixed(gridSize),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = dimensionResource(DesignSystem.dimen.zcds_margin_padding_size_medium))
        ) {
            item(
                key = null,
                span = { GridItemSpan(gridSize) },
                contentType = null,
                content = {
                    ZEMTText(text = title, style = titleStyle, textStyle = textType)
                }
            )
            item(
                key = null,
                span = { GridItemSpan(gridSize) },
                contentType = null,
                content = { ZEMTCollaboratorSearcher(onClickListener = onSearchClick) }
            )
            itemsIndexed(
                collaboratorList,
                key = { _, item -> item.collaboratorId }) { index, collaborator ->
                ZEMTCollaboratorView(
                    id = collaborator.collaboratorId,
                    name = collaborator.name,
                    photoUrl = collaborator.photoUrl,
                    selected = selectedCollaboratorIndex == index,
                    enabled = collaborator.talentsCompleted,
                    onClick = { id, name, profileUrl ->
                        if (collaborator.talentsCompleted.not()) noTalentsCompletedAction(name, id)
                        else if (viewType == ZEMTCollaboratorListViewType.TALENTS) {
                            onCollaboratorClick(id, name, profileUrl)
                        } else {
                            selectedCollaboratorIndex = index
                        }
                    }
                )
            }

        }
        if (viewType == ZEMTCollaboratorListViewType.MAKE_CONVERSATION) {
            ZEMTButton(
                text = stringResource(R.string.zemt_continue),
                onClick = {
                    val collaborator = collaboratorList.getOrNull(selectedCollaboratorIndex) ?: return@ZEMTButton
                    if (collaborator.talentsCompleted) {
                        onCollaboratorClick(
                            collaborator.collaboratorId,
                            collaborator.name,
                            collaborator.photoUrl
                        )
                    } else noTalentsCompletedAction(collaborator.name, collaborator.collaboratorId)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(DesignSystem.dimen.zcds_margin_padding_size_xlarge))
                    .padding(top = dimensionResource(DesignSystem.dimen.zcds_margin_padding_size_medium))
            )

        }
        val gridLimitToShowLottie = 8
        if (collaboratorList.size > gridLimitToShowLottie
            || collaboratorList.isEmpty()
            || viewType == ZEMTCollaboratorListViewType.MAKE_CONVERSATION
            ) return

        val lottie by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.zemt_colaboradores)
        )
        LottieAnimation(
            composition = lottie,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .aspectRatio(256 / 180f)
                .padding(horizontal = 44.dp)
        )
    }
}

@Preview(name = "short list")
@Composable
private fun ZEMTCollaboratorsViewPreview() {
    ZEMTCollaboratorListView(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp),
        collaboratorList = ZEMTCollaboratorsMockData.collaboratorsMockList,
        onCollaboratorClick = { _, _, _ -> },
        captions = ZEMTCollaboratorListCaptions(
            collaboratorsCaption = stringResource(R.string.zemt_talent_menu_collaborators),
            selectCollaboratorCaption = stringResource(R.string.zemt_talent_menu_collaborators),
            makeConversationCaption = stringResource(R.string.zemt_conversations_choose_collaborator)
        ),
        onSearchClick = { }
    )
}

@Preview(name = "large list")
@Composable
private fun ZEMTCollaboratorsViewPreview2() {
    ZEMTCollaboratorListView(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp),
        collaboratorList = ZEMTCollaboratorsMockData.collaboratorsMockListLarge,
        onCollaboratorClick = { _, _, _ -> },
        viewType = ZEMTCollaboratorListViewType.MAKE_CONVERSATION,
        captions = ZEMTCollaboratorListCaptions(
            collaboratorsCaption = stringResource(R.string.zemt_talent_menu_collaborators),
            selectCollaboratorCaption = stringResource(R.string.zemt_talent_menu_collaborators),
            makeConversationCaption = stringResource(R.string.zemt_conversations_choose_collaborator)
        ),
        onSearchClick = { }
    )
}