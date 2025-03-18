package com.upax.zemytalents.ui.modules.confirm.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ZEMTBottomSheetNoDominantTalents(
    talents: List<ZEMTTalent>,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(
                    bottom = 24.dp,
                    top = 16.dp,
                    start = 12.dp,
                    end = 12.dp
                )
        ) {
            talents.forEachIndexed { index, talent ->
                ZEMTDropdownGroup(
                    groupTitle = "${index + 6}. ${talent.name}",
                    groupIcon = talent.getIconFromId(),
                    endIcon = R.drawable.zemt_ic_padlock_closed_outlined,
                    endIconTint = Color(ZCDSColorUtils.getPrimaryColor()),
                    endIconSize = 24.dp,
                    isOpenable = false,
                    onGroupClick = {
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}