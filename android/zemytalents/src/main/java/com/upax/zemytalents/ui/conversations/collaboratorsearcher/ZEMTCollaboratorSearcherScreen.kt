package com.upax.zemytalents.ui.conversations.collaboratorsearcher

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.upax.zcdesignsystem.widget.ZCDSPhotoView
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorsMockData
import com.upax.zemytalents.ui.shared.composables.ZEMTSearchView
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTCollaboratorSearcherScreen(
    collaborators: List<ZEMTCollaboratorInCharge>? = null,
    modifier: Modifier = Modifier,
    onSearchTextChange: (String) -> Unit,
    onCollaboratorClick: (ZEMTCollaboratorInCharge) -> Unit,
    onBackPressed: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(RDS.color.zcds_white))
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .padding(end = dimensionResource(RDS.dimen.zcds_margin_padding_size_large))
                .padding(start = dimensionResource(RDS.dimen.zcds_margin_padding_size_small))
                .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_medium))
        ) {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(painterResource(RDS.drawable.zcds_ic_arrow_back), contentDescription = null)
            }
            ZEMTSearchView(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onTextChange = onSearchTextChange
            )
        }

        if (collaborators != null) {
            ZEMTCollaboratorList(
                collaborators,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(RDS.dimen.zcds_margin_padding_size_large))
                    .padding(top = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large)),
                onCollaboratorClick = onCollaboratorClick
            )
        }
    }

}

@Composable
private fun ZEMTCollaboratorList(
    collaborators: List<ZEMTCollaboratorInCharge>,
    modifier: Modifier = Modifier,
    onCollaboratorClick: (ZEMTCollaboratorInCharge) -> Unit
) {
    Column(modifier = modifier) {
        ZEMTText(
            text = stringResource(R.string.zemt_total_results, collaborators.size),
            style = RDS.style.TextAppearance_ZCDSApp_Header05,
            modifier = Modifier.padding(bottom = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
        )
        LazyColumn {
            items(collaborators, key = { it.collaboratorId }) { collaborator ->
                ZEMTItemCollaborator(
                    collaborator = collaborator,
                    modifier = Modifier.clickable { onCollaboratorClick(collaborator) }
                )
            }
        }
    }
}

@Composable
private fun ZEMTItemCollaborator(
    collaborator: ZEMTCollaboratorInCharge,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = dimensionResource(RDS.dimen.zcds_margin_padding_size_medium))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AndroidView(
            factory = { context ->
                ZCDSPhotoView(context).apply {
                    setInfo(name = collaborator.name, url = collaborator.photoUrl)
                }
            },
            modifier = Modifier.size(40.dp)
        )
        ZEMTText(
            style = RDS.style.TextAppearance_ZCDSApp_BodyMedium,
            text = collaborator.name,
            modifier = Modifier
                .padding(horizontal = dimensionResource(RDS.dimen.zcds_margin_padding_size_medium))
        )
    }
}


@Composable
@Preview(
    device = Devices.PIXEL_7A
)
private fun ZEMTCollaboratorSearcherScreenPreview() {
    ZEMTCollaboratorSearcherScreen(
        collaborators = null,
        onSearchTextChange = { },
        onCollaboratorClick = { },
        onBackPressed = { }
    )
}

@Composable
@Preview(
    device = Devices.PIXEL_7A
)
private fun ZEMTCollaboratorSearcherScreenWithCollaboratorsPreview() {
    ZEMTCollaboratorSearcherScreen(
        collaborators = ZEMTCollaboratorsMockData.collaboratorsMockList,
        onSearchTextChange = { },
        onCollaboratorClick = { },
        onBackPressed = { }
    )
}