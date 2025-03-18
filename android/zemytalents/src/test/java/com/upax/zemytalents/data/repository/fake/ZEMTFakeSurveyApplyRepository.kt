package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyRepository
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal class ZEMTFakeSurveyApplyRepository(
    private val id: String = ZEMTRandomValuesUtil.getString()
): ZEMTSurveyApplyRepository {

    var finished = false

    override suspend fun getId(): ZEMTSurveyId {
        return ZEMTSurveyId(id)
    }

    override suspend fun finish() {
        finished = true
    }

}