package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverDownloader

internal class ZEMTFakeSurveyDownloader(
    private val errorToReturn: ZEMTDataError? = null,
    private val needToDownload: Boolean = true
): ZEMTSurveyDiscoverDownloader {

    override suspend fun download(): ZEMTResult<Unit, ZEMTDataError> {
        return if (errorToReturn == null) {
            ZEMTResult.Success(Unit)
        } else {
            ZEMTResult.Error(errorToReturn)
        }
    }

    override suspend fun needToDownload(): Boolean = needToDownload
}