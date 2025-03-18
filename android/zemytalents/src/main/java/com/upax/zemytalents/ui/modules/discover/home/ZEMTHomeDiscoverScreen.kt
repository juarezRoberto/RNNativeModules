package com.upax.zemytalents.ui.modules.discover.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.shared.mock.ZEMTMockModulesData
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.shared.composables.modulelist.ZEMTModuleList
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfile
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileStages
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileUserUiModel
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTHomeDiscoverScreen(
    modifier: Modifier = Modifier,
    user: ZCSIUser?,
    modules: List<ZEMTModuleUiModel>,
    discoverModuleProgress: Float,
    navigateToDiscoverModule: () -> Unit
) {
    Box(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            user?.let {
                ZEMTTalentsProfile(
                    stage = ZEMTTalentsProfileStages.Discover,
                    data = ZEMTTalentsProfileUserUiModel(
                        userName = user.getFullName(),
                        userProfileUrl = user.photo
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(267.dp),
                    onTalentClick = {},
                    onTalentFocusListener = {}
                )
            }
            ZEMTText(
                stringResource(R.string.zemt_known_them),
                style = RDS.style.TextAppearance_ZCDSApp_Header03,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
            )
            ZEMTText(
                stringResource(R.string.zemt_home_discover_description),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f, true))
            ZEMTModuleList(
                moduleList = modules,
                discoverModuleProgress = discoverModuleProgress,
                navigateToModule = { navigateToDiscoverModule() },
                scrollTarget = ZEMTModuleStage.DISCOVER,
                scrollPrevious = ZEMTModuleStage.DISCOVER,
                modifier = Modifier.padding(bottom = 60.dp)
            )
            ZEMTButton(
                text = stringResource(R.string.zemt_discover_talents_v2),
                onClick = { navigateToDiscoverModule() },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTHomeDiscoverScreenPreview() {
    ZEMTHomeDiscoverScreen(
        user = ZCSIUser(),
        modules = ZEMTMockModulesData.getModules(),
        navigateToDiscoverModule = {},
        discoverModuleProgress = 0.01f
    )
}