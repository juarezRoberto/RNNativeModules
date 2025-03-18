package com.upax.zemytalents.ui.shared.models

import android.os.Parcelable
import com.upax.zemytalents.domain.models.modules.ZEMTModuleMultimediaType
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ZEMTModuleMultimediaUiModel(
    val url: String,
    val type: ZEMTModuleMultimediaType,
    val order: Int,
    val title: String,
    val duration: String,
    val description: String,
    val urlThumbnail: String
) : Parcelable
