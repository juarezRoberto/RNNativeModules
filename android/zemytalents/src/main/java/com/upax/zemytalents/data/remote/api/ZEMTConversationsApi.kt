package com.upax.zemytalents.data.remote.api

import com.upax.zcservicecoordinator.expose.ZCSCEncrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptCall
import com.upax.zcservicecoordinator.expose.models.ZCSCBaseResponseV2
import com.upax.zemytalents.data.remote.requests.ZEMTOrganizationalChartRequest
import com.upax.zemytalents.data.remote.requests.ZEMTSaveConversationBodyRequest
import com.upax.zemytalents.data.remote.responses.ZEMTOrganizationalChartResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTConversationListResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTConversationsHistoryResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTPhrasesResponse
import com.upax.zemytalents.data.remote.responses.conversations.ZEMTSaveConversationResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ZEMTConversationsApi {

    /**
     * @param type: team or boss of type [ZEOCOrganizationalChartType], team will return the team members that are lead by the collaborator, boss will return the boss of the collaborator
     * @return
     * */
    @POST("v2/organization-chart/mobile/{companyId}/{type}")
    @ZCSCEncryptCall
    suspend fun getOrganizationalChart(
        @Body request: ZEMTOrganizationalChartRequest,
        @Path("companyId") companyId: String,
        @Path("type") type: String = ZEOCOrganizationalChartType.TEAM.key
    ): ZCSCBaseResponseV2<ZEMTOrganizationalChartResponse, Unit>

    @GET("talents/conversations")
    @ZCSCEncryptCall
    suspend fun getConversations(
        @Query("collaborator-id") @ZCSCEncrypt collaboratorId: String,
    ): ZCSCBaseResponseV2<ZEMTConversationListResponse, Unit>

    @GET("talents/phrases")
    @ZCSCEncryptCall
    suspend fun getPhrases(
        @Query("conversation-id") conversationId: String,
        @Query("collaborator-id") @ZCSCEncrypt collaboratorId: String,
    ): ZCSCBaseResponseV2<ZEMTPhrasesResponse, Unit>

    @POST("talents/conversations/{conversationId}")
    @ZCSCEncryptCall
    suspend fun saveConversation(
        @Path("conversationId") conversationId: String,
        @Body body: ZEMTSaveConversationBodyRequest
    ): ZCSCBaseResponseV2<ZEMTSaveConversationResponseWrapper, Unit>

    @GET("talents/conversations/history")
    @ZCSCEncryptCall
    suspend fun getConversationsHistory(
        @Query("collaborator-id") @ZCSCEncrypt collaboratorId: String,
        @Query("boss-id") @ZCSCEncrypt bossId: String,
        @Query("conversation-id") conversationId: String,
    ): ZCSCBaseResponseV2<ZEMTConversationsHistoryResponse, Unit>
}

/**
 * Organizational chart type
 * @property key: key of the type
 *
 * @param TEAM: team or boss of type [ZEOCOrganizationalChartType], team will return the team members that are lead by the collaborator, boss will return the boss of the collaborator
 * @param BOSS: team or boss of type [ZEOCOrganizationalChartType], team will return the team members that are lead by the collaborator, boss will return the boss of the collaborator
 * */
internal enum class ZEOCOrganizationalChartType(val key: String) {
    TEAM(TEAM_KEY), BOSS(BOSS_KEY)
}

private const val TEAM_KEY = "team"
private const val BOSS_KEY = "boss"