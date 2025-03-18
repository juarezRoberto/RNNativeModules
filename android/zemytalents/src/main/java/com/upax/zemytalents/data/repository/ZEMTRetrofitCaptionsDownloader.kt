package com.upax.zemytalents.data.repository

import com.upax.zcservicecoordinator.expose.models.ZCSCResult
import com.upax.zcservicecoordinator.expose.utils.performNetworkCallV3
import com.upax.zemytalents.data.remote.api.ZEMTCaptionsApi
import com.upax.zemytalents.data.remote.mapper.ZEMTCaptionsResponseMapper
import com.upax.zemytalents.data.remote.mapper.toDataError
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTCaptionsStorage
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import com.upax.zemytalents.domain.usecases.ZEMTCaptionsDownloader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTRetrofitCaptionsDownloader(
    private val captionsApi: ZEMTCaptionsApi,
    private val mapper: ZEMTCaptionsResponseMapper,
    private val userRepository: ZEMTUserRepository,
    private val captionsSaver: ZEMTCaptionsStorage
) : ZEMTCaptionsDownloader {

    override fun download(): Flow<ZEMTConversationResult<Unit>> = flow {
        emit(ZEMTConversationResult.Loading)
        val result = performNetworkCallV3(
            call = {
                captionsApi.getCaptions(userRepository.collaboratorId)
            },
            onSuccess = { response ->
                val captions = mapper.invoke(response.data?.captions)
                captionsSaver.save(captions)
            },
            onError = { error -> error.toDataError() }
        )

        val response = when (result) {
            is ZCSCResult.Error -> ZEMTConversationResult.Error(Exception())
            is ZCSCResult.Success -> ZEMTConversationResult.Success(Unit)
        }
        emit(response)
    }

}