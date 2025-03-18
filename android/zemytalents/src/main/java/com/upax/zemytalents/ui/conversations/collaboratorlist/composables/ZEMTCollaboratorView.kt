package com.upax.zemytalents.ui.conversations.collaboratorlist.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.upax.zcdesignsystem.widget.ZCDSPhotoView
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTCollaboratorView(
    id: String,
    name: String,
    photoUrl: String,
    onClick: (id: String, name: String, profileImageUrl: String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false
) {
    val borderWith = 1.2.dp
    val padding = 4.dp
    val mainColor = colorResource(DesignSystem.color.zcds_success)
    val secondaryColor = colorResource(DesignSystem.color.zcds_white)
    val iconSize = 12.dp

    val selectedModifier = if (selected) Modifier
        .border(
            width = borderWith,
            shape = CircleShape,
            color = mainColor
        )
        .padding(padding) else Modifier.padding(padding + borderWith)


    Column(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.5f)
            .clickable( onClick = { onClick(id, name, photoUrl) }),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Box(modifier = selectedModifier) {
                AndroidView(
                    factory = { context ->
                        ZCDSPhotoView(context).apply {
                            setInfo(name = name, url = photoUrl)
                        }
                    }, modifier = Modifier
                        .size(55.dp)

                )

            }
            if (selected) Icon(
                painter = painterResource(id = DesignSystem.drawable.zcds_ic_check_outlined),
                tint = secondaryColor,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.TopEnd)
                    .offset(x = -iconSize / 3, y = iconSize / 2)
                    .background(color = mainColor, shape = CircleShape)
                    .border(
                        width = borderWith.times(1.2f),
                        color = secondaryColor,
                        shape = CircleShape
                    )
                    .padding(1.dp)


            )
        }
        ZEMTText(text = name, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview
@Composable
private fun ZEMTCollaboratorViewPreview() {
    ZEMTCollaboratorView(
        id = "1",
        name = "Pedro Estevez",
        photoUrl = "",
        modifier = Modifier.width(81.dp),
        onClick = { _, _, _ -> }
    )
}

@Preview
@Composable
private fun ZEMTCollaboratorViewPreview2() {
    ZEMTCollaboratorView(
        id = "1",
        name = "Pedro Estevez",
        photoUrl = "",
        modifier = Modifier.width(81.dp),
        onClick = { _, _, _ -> },
        selected = true
    )
}