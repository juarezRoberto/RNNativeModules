package com.upax.zemytalents.data.remote.api

import com.upax.zcservicecoordinator.expose.ZCSCEncrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptCall
import com.upax.zcservicecoordinator.expose.models.ZCSCBaseResponseV2
import com.upax.zemytalents.data.remote.responses.ZEMTCaptionsResponse import retrofit2.http.GET
import retrofit2.http.Query

internal interface ZEMTCaptionsApi {

    @GET("talents/captions")
    @ZCSCEncryptCall
    suspend fun getCaptions(
        @Query("collaborator-id") @ZCSCEncrypt collaboratorId: String
    ): ZCSCBaseResponseV2<ZEMTCaptionsResponse, Unit>

}