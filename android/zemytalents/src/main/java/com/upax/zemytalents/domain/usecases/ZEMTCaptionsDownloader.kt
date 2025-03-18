package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import kotlinx.coroutines.flow.Flow

internal interface ZEMTCaptionsDownloader {

     fun download(): Flow<ZEMTConversationResult<Unit>>

}