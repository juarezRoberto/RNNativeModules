package com.upax.zemytalents.data.repository

import android.util.Log
import com.upax.zcservicecoordinator.expose.models.ZCSCResult
import com.upax.zcservicecoordinator.expose.utils.performNetworkCallV3
import com.upax.zcsessioninfo.domain.model.ZCSILeader
import com.upax.zcsessioninfo.domain.repository.ZCSISessionInfo
import com.upax.zemytalents.data.local.database.conversations.ZEMTConversationsDatabase
import com.upax.zemytalents.data.remote.api.ZEMTConversationsApi
import com.upax.zemytalents.data.remote.api.ZEOCOrganizationalChartType
import com.upax.zemytalents.data.remote.mapper.ZEMTConversationsMapper.toModel
import com.upax.zemytalents.data.remote.mapper.ZEMTConversationsMapper.toRoomEntity
import com.upax.zemytalents.data.remote.requests.ZEMTOrganizationalChartRequest
import com.upax.zemytalents.data.remote.requests.ZEMTSaveConversationBodyRequest
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationHistory
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.models.conversations.ZEMTPhrase
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class ZEMTConversationsRepositoryImpl(
    private val api: ZEMTConversationsApi,
    private val db: ZEMTConversationsDatabase,
    private val sessionInfo: ZCSISessionInfo
) : ZEMTConversationsRepository {
    override suspend fun fetchCollaboratorsInCharge(
        collaboratorId: String,
        companyId: String,
        type: ZEOCOrganizationalChartType
    ): ZEMTConversationResult<List<ZEMTCollaboratorInCharge>> = withContext(Dispatchers.IO) {
        val result = performNetworkCallV3(
            call = {
                api.getOrganizationalChart(
                    request = ZEMTOrganizationalChartRequest(collaboratorId = collaboratorId),
                    companyId = companyId,
                    type = type.key
                )
            },
            onError = {
                val errorInformation = """
                   Message: ${it.message}
                   Code: ${it.code}
                   HttpCode: ${it.httpCode}
                   Reason: ${it.reason}
                   Details: ${it.details}
                   Meta: ${it.meta}
                """
                Log.e("ZEMTOrganizationalChartRemoteDataSource", errorInformation)
            },
            onSuccess = {
                sessionInfo.saveLeader(ZCSILeader(zeusId = it.data?.organizationalChart?.idLeader.orEmpty()))
                val collaboratorsResponse = it.data?.organizationalChart?.collaborators.orEmpty()
                db.collaboratorInChargeDao().insertAll(collaboratorsResponse.map { response ->
                    response.toRoomEntity(collaboratorId)
                })
                db.collaboratorInChargeDao()
                    .getCollaboratorsInCharge(collaboratorId)
                    .map { entity -> entity.toModel() }
            })
        return@withContext when (result) {
            is ZCSCResult.Error -> ZEMTConversationResult.Error(Exception())
            is ZCSCResult.Success -> ZEMTConversationResult.Success(result.data)
        }
    }

    override suspend fun getCollaboratorsInCharge(collaboratorId: String): ZEMTConversationResult<List<ZEMTCollaboratorInCharge>> =
        withContext(Dispatchers.IO) {
            val result = db.collaboratorInChargeDao().getCollaboratorsInCharge(collaboratorId)
                .map { entity -> entity.toModel() }
            return@withContext ZEMTConversationResult.Success(result)
        }

    override suspend fun fetchConversations(collaboratorId: String): ZEMTConversationResult<List<ZEMTConversation>> =
        withContext(Dispatchers.IO) {
            val result = performNetworkCallV3(
                call = {
                    api.getConversations(collaboratorId = collaboratorId)
                },
                onError = {
                    val errorInformation = """
                   Message: ${it.message}
                   Code: ${it.code}
                   HttpCode: ${it.httpCode}
                   Reason: ${it.reason}
                   Details: ${it.details}
                   Meta: ${it.meta}
                """
                    Log.e("ZEMTOrganizationalChartRemoteDataSource", errorInformation)
                },
                onSuccess = {
                    val collaboratorsResponse = it.data?.conversations.orEmpty()
                    val conversationsEntityList =
                        collaboratorsResponse.mapIndexed { index, response ->
                            response.toRoomEntity(
                                index
                            )
                        }
                    db.conversationDao().insertAll(conversationsEntityList)
                    db.conversationDao().getConversations().map { entity -> entity.toModel() }
                })

            return@withContext when (result) {
                is ZCSCResult.Error -> ZEMTConversationResult.Error(Exception())
                is ZCSCResult.Success -> ZEMTConversationResult.Success(result.data)
            }
        }

    override suspend fun getConversations(): ZEMTConversationResult<List<ZEMTConversation>> =
        withContext(Dispatchers.IO) {
            val result = db.conversationDao().getConversations().map { entity -> entity.toModel() }
            return@withContext ZEMTConversationResult.Success(result)
        }

    override suspend fun fetchPhrases(
        conversationId: String,
        collaboratorId: String
    ): ZEMTConversationResult<List<ZEMTPhrase>> =
        withContext(Dispatchers.IO) {
            val result = performNetworkCallV3(
                call = {
                    api.getPhrases(conversationId = conversationId, collaboratorId = collaboratorId)
                },
                onError = {
                    val errorInformation = """
                   Message: ${it.message}
                   Code: ${it.code}
                   HttpCode: ${it.httpCode}
                   Reason: ${it.reason}
                   Details: ${it.details}
                   Meta: ${it.meta}
                """
                    Log.e("ZEMTPhrasesRemoteDataSource", errorInformation)

                },
                onSuccess = {
                    val phrasesResponse = it.data?.phrases.orEmpty()
                    db.phraseDao().insertAll(phrasesResponse.map { response ->
                        response.toRoomEntity(conversationId, collaboratorId)
                    })
                    db.phraseDao().getPhrases(
                        conversationId = conversationId,
                        collaboratorId = collaboratorId
                    ).map { entity -> entity.toModel() }
                })
            return@withContext when (result) {
                is ZCSCResult.Error -> ZEMTConversationResult.Error(Exception())
                is ZCSCResult.Success -> ZEMTConversationResult.Success(result.data)
            }
        }

    override suspend fun getPhrases(
        conversationId: String,
        collaboratorId: String
    ): ZEMTConversationResult<List<ZEMTPhrase>> =
        withContext(Dispatchers.IO) {
            val result =
                db.phraseDao()
                    .getPhrases(conversationId = conversationId, collaboratorId = collaboratorId)
                    .map { entity -> entity.toModel() }
            return@withContext ZEMTConversationResult.Success(result)
        }

    override fun getProgress(
        conversationOwnerId: String,
        collaboratorId: String
    ): Flow<ZEMTMakeConversationProgress?> =
        db.makeConversationProgressDao().getProgress(conversationOwnerId, collaboratorId)
            .map { entity -> entity?.toModel() }

    override suspend fun getProgressNotObservable(
        conversationOwnerId: String,
        collaboratorId: String
    ): ZEMTMakeConversationProgress? =
        db.makeConversationProgressDao()
            .getProgressNotObservable(conversationOwnerId, collaboratorId)?.toModel()

    override suspend fun updateProgress(progress: ZEMTMakeConversationProgress) {
        db.makeConversationProgressDao()
            .insert(progress.toRoomEntity(progress.conversationOwnerId, progress.collaboratorId))
    }

    override suspend fun deleteProgressById(conversationOwnerId: String, collaboratorId: String) {
        db.makeConversationProgressDao().deleteProgressById(conversationOwnerId, collaboratorId)
    }

    override suspend fun saveConversation(
        conversationId: String,
        body: ZEMTSaveConversationBodyRequest
    ): ZEMTConversationResult<Unit> = withContext(Dispatchers.IO) {
        val result = performNetworkCallV3(
            call = {
                api.saveConversation(conversationId, body)
            },
            onError = {
                val errorInformation = """
                   Message: ${it.message}
                   Code: ${it.code}
                   HttpCode: ${it.httpCode}
                   Reason: ${it.reason}
                   Details: ${it.details}
                   Meta: ${it.meta}
                """
                Log.e("ZEMTConversationsRemoteDataSource", errorInformation)
            },
            onSuccess = {
                val response = it.data?.conversation?.conversationHistoryId
                if (response != null) ZEMTConversationResult.Success(Unit) else ZEMTConversationResult.Error(
                    Exception()
                )
            })
        return@withContext when (result) {
            is ZCSCResult.Error -> ZEMTConversationResult.Error(Exception())
            is ZCSCResult.Success -> result.data
        }
    }

    override suspend fun fetchConversationsHistory(
        collaboratorId: String,
        bossId: String,
        conversationId: String
    ): ZEMTConversationResult<List<ZEMTConversationHistory>> = withContext(Dispatchers.IO) {
        val result = performNetworkCallV3(
            call = {
                api.getConversationsHistory(
                    collaboratorId = collaboratorId,
                    bossId = bossId,
                    conversationId = conversationId
                )
            },
            onSuccess = {
                val response = it.data?.conversations.orEmpty()
                response.map { response -> response.toModel() }
            }, onError = {
                val errorInformation = """
                   Message: ${it.message}
                   Code: ${it.code}
                   HttpCode: ${it.httpCode}
                   Reason: ${it.reason}
                   Details: ${it.details}
                   Meta: ${it.meta}
                """
                Log.e("ZEMTConversationsRemoteDataSource", errorInformation)
                it
            })
        return@withContext when (result) {
            is ZCSCResult.Error -> ZEMTConversationResult.Error(Exception())
            is ZCSCResult.Success -> ZEMTConversationResult.Success(result.data)
        }
    }
}