package com.upax.zemytalents.data.remote.api

import com.upax.zcservicecoordinator.expose.ZCSCEncrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptCall
import com.upax.zcservicecoordinator.expose.models.ZCSCBaseResponseV2
import com.upax.zemytalents.data.remote.requests.ZEMTSyncSurveyDiscoverAnswersRequest
import com.upax.zemytalents.data.remote.responses.ZEMTSurveyDiscoverResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ZEMTSurveyDiscoverApiService {

    @GET("talents/surveys/{survey-id}")
    @ZCSCEncryptCall
    suspend fun getSurvey(
        @Path("survey-id") surveyId: String,
        @Query("collaborator_id") @ZCSCEncrypt collaboratorId: String
    ): ZCSCBaseResponseV2<ZEMTSurveyDiscoverResponse, Unit>

    @POST("talents/surveys/{survey-id}")
    @ZCSCEncryptCall
    suspend fun syncSurvey(
        @Path("survey-id") surveyId: String,
        @Body request: ZEMTSyncSurveyDiscoverAnswersRequest
    ): ZCSCBaseResponseV2<ZEMTSurveyDiscoverResponse, Unit>

}