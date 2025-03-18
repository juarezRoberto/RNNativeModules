package com.upax.zemytalents.ui.mytalents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.ZEMTTalents
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId
import com.upax.zemytalents.ui.modules.shared.mock.ZEMTMockModulesData
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownGroup
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfile
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileStages
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileUserUiModel
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTMyTalentsScreen(
    modifier: Modifier = Modifier,
    uiState: ZEMTMyTalentsUiState,
    onShowDetailDialog: (ZEMTTalent) -> Unit
) {
    Box(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .padding(all = dimensionResource(RDS.dimen.zcds_margin_padding_size_large))
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            uiState.user?.let {
                ZEMTText(
                    text = stringResource(R.string.zemt_greeting_user, it.name),
                    style = RDS.style.TextAppearance_ZCDSApp_Header04
                )
                ZEMTText(
                    modifier = Modifier.padding(
                        top = dimensionResource(RDS.dimen.zcds_margin_padding_size_small)
                    ),
                    text = stringResource(R.string.zemt_click_buttons_on_talents_profile_to_show_detail)
                )
                ZEMTTalentsProfile(
                    stage = ZEMTTalentsProfileStages.Complete(uiState.talents?.dominantTalents.orEmpty()),
                    data = ZEMTTalentsProfileUserUiModel(
                        userName = uiState.user.getFullName(),
                        userProfileUrl = uiState.user.photo
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_xxlarge))
                        .height(267.dp),
                    onTalentClick = {
                        if (it != null) {
                            onShowDetailDialog(it)
                        }
                    },
                    onTalentFocusListener = { talentState ->
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_large))
                ) {
                    uiState.talents?.notDominantTalents?.forEachIndexed { index, talent ->
                        ZEMTDropdownGroup(
                            groupTitle = "${6 + index}. ${talent.name}",
                            groupIcon = talent.getIconFromId(),
                            isOpenable = false,
                            onGroupClick = {
                                onShowDetailDialog.invoke(talent)
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ZEMTMyTalentsScreenPreview() {
    ZEMTMyTalentsScreen(
        uiState = ZEMTMyTalentsUiState(
            user = ZCSIUser(name = "Robert"),
            talents = ZEMTTalents(
                dominantTalents = ZEMTMockModulesData.getTalents(),
                notDominantTalents = ZEMTMockModulesData.getTalents()
            )
        ),
        onShowDetailDialog = {}
    )
}