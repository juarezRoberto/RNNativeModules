package com.upax.zemytalents.data.remote.api

import com.upax.zcservicecoordinator.expose.ZCSCEncrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptCall
import com.upax.zcservicecoordinator.expose.models.ZCSCBaseResponseV2
import com.upax.zemytalents.data.remote.requests.ZEMTTalentsCompletedByListRequest
import com.upax.zemytalents.data.remote.responses.ZEMTSurveyTalentsWrapper
import com.upax.zemytalents.data.remote.responses.ZEMTGetTalentsResponseWrapper
import com.upax.zemytalents.data.remote.responses.ZEMTModuleResponseWrapper
import com.upax.zemytalents.data.remote.responses.ZEMTTalentsCompletedByListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ZEMTMyTalentsApi {

    @GET("talents/modules")
    @ZCSCEncryptCall
    suspend fun getModules(
        @Query("collaborator-id") @ZCSCEncrypt collaboratorId: String
    ): ZCSCBaseResponseV2<ZEMTModuleResponseWrapper, Unit>

    @GET("talents/summary")
    @ZCSCEncryptCall
    suspend fun getTalents(
        @Query("collaborator-id") @ZCSCEncrypt collaboratorId: String
    ): ZCSCBaseResponseV2<ZEMTGetTalentsResponseWrapper, Unit>

    @GET("talents/surveys/{survey-id}/secondary-modules")
    @ZCSCEncryptCall
    suspend fun getSurveyTalent(
        @Path("survey-id") surveyId: String,
        @Query("collaborator_id") @ZCSCEncrypt collaboratorId: String
    ): ZCSCBaseResponseV2<ZEMTSurveyTalentsWrapper, Unit>

    @POST("talents/progress-stages")
    @ZCSCEncryptCall
    suspend fun getTalentsCompletedByList(@Body request: ZEMTTalentsCompletedByListRequest): ZCSCBaseResponseV2<ZEMTTalentsCompletedByListResponse, Unit>
}