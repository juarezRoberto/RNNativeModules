package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.ZEMTResult

internal interface ZEMTSurveyDiscoverDownloader {

    suspend fun download(): ZEMTResult<Unit, ZEMTDataError>

    suspend fun needToDownload(): Boolean

}