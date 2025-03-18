package com.upax.zemytalents.data.remote.requests

import com.google.gson.annotations.SerializedName
import com.upax.zcservicecoordinator.expose.ZCSCEncrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptClass

@ZCSCEncryptClass
data class ZEMTTalentsCompletedByListRequest(
    @field:ZCSCEncrypt
    @SerializedName("collaborators_id")
    val collaboratorsIdList: List<String>
)