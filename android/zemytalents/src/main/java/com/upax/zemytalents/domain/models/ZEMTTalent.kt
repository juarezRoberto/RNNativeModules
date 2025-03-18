package com.upax.zemytalents.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ZEMTTalent(
    val id: Int,
    val name: String,
    val description: String,
    val isTempTalent: Boolean = true,
    val attachment: List<ZEMTTalentAttachment> = emptyList()
) : Parcelable