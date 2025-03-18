package com.upax.zemytalents.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.upax.zcservicecoordinator.expose.ZCSCDecrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptClass

@ZCSCEncryptClass
internal data class ZEMTOrganizationalChartResponse(
    @SerializedName("organization_chart")
    val organizationalChart: ZEMTOrganizationalChartResponseItem?,
)

@ZCSCEncryptClass
internal data class ZEMTOrganizationalChartResponseItem(
    @field:ZCSCDecrypt
    @SerializedName("id")
    val idLeader: String?,
    @field:ZCSCDecrypt
    @SerializedName("identifier_app")
    val employeeNumberLeader: String?,
    @SerializedName("name")
    val nameLeader: String?,
    @SerializedName("position")
    val jobLeader: String?,
    @SerializedName("phone")
    val phoneNumberLeader: String?,
    @SerializedName("email")
    val emailLeader: String?,
    @SerializedName("photo")
    val photoLeader: String?,
    @SerializedName("total_collaborators")
    val totalCollaborators: Int?,
    @SerializedName("collaborators")
    val collaborators: List<ZEMTCollaboratorResponse>?
)

@ZCSCEncryptClass
internal data class ZEMTCollaboratorResponse(
    @field:ZCSCDecrypt
    @SerializedName("id")
    val id: String?,
    @field:ZCSCDecrypt
    @SerializedName("identifier_app")
    val employeeNumber: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("position")
    val job: String?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("total_collaborators")
    val totalCollaborators: Int?,
    @SerializedName("short_name")
    val shortName: String?
)