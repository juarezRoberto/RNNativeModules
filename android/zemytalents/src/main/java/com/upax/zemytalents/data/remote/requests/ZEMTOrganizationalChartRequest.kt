package com.upax.zemytalents.data.remote.requests

import com.google.gson.annotations.SerializedName
import com.upax.zcservicecoordinator.expose.ZCSCEncrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptClass

@ZCSCEncryptClass
internal data class ZEMTOrganizationalChartRequest(
    @field:ZCSCEncrypt
    @SerializedName("id")
    val collaboratorId: String,
)
