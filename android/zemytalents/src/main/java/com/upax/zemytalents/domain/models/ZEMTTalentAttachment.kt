package com.upax.zemytalents.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ZEMTTalentAttachment(
    val type: ZEMTAttachmentType,
    val url: String
) : Parcelable

internal enum class ZEMTAttachmentType(val id: Int) {
    IMAGE(2),
    VIDEO(1),
    LOTTIE(3),
    ICON(4),
    NONE(-1);
}
