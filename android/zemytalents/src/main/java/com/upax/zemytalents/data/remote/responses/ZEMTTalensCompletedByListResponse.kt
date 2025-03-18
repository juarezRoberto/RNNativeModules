package com.upax.zemytalents.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.upax.zccommon.extensions.EMPTY

data class ZEMTTalentsCompletedByListResponse(
    @SerializedName("progress_stages")
    val progressStages: List<ZEMTTalentsCompletedByListResponseItem>? = emptyList()
)

data class ZEMTTalentsCompletedByListResponseItem(
    @SerializedName("collaborator_id")
    val talentId: String? = String.EMPTY,
    @SerializedName("is_complete")
    val completed: Boolean? = false
)
