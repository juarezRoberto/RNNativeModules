package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.remote.api.ZEOCOrganizationalChartType
import com.upax.zemytalents.data.remote.requests.ZEMTSaveConversationBodyRequest
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationHistory
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.models.conversations.ZEMTPhrase
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationProgress
import kotlinx.coroutines.flow.Flow

internal interface ZEMTConversationsRepository {
    suspend fun fetchCollaboratorsInCharge(
        collaboratorId: String,
        companyId: String,
        type: ZEOCOrganizationalChartType = ZEOCOrganizationalChartType.TEAM
    ): ZEMTConversationResult<List<ZEMTCollaboratorInCharge>>

    suspend fun getCollaboratorsInCharge(collaboratorId: String): ZEMTConversationResult<List<ZEMTCollaboratorInCharge>>

    suspend fun fetchConversations(collaboratorId: String): ZEMTConversationResult<List<ZEMTConversation>>
    suspend fun getConversations(): ZEMTConversationResult<List<ZEMTConversation>>

    suspend fun fetchPhrases(
        conversationId: String,
        collaboratorId: String
    ): ZEMTConversationResult<List<ZEMTPhrase>>

    suspend fun getPhrases(
        conversationId: String,
        collaboratorId: String
    ): ZEMTConversationResult<List<ZEMTPhrase>>

    fun getProgress(
        conversationOwnerId: String,
        collaboratorId: String
    ): Flow<ZEMTMakeConversationProgress?>

    suspend fun getProgressNotObservable(
        conversationOwnerId: String,
        collaboratorId: String
    ): ZEMTMakeConversationProgress?

    suspend fun updateProgress(progress: ZEMTMakeConversationProgress)

    suspend fun deleteProgressById(conversationOwnerId: String, collaboratorId: String)

    suspend fun saveConversation(
        conversationId: String,
        body: ZEMTSaveConversationBodyRequest
    ): ZEMTConversationResult<Unit>

    suspend fun fetchConversationsHistory(
        collaboratorId: String,
        bossId: String,
        conversationId: String
    ): ZEMTConversationResult<List<ZEMTConversationHistory>>
}