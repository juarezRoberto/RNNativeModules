package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.ZEMTCollaboratorTalentStatus
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.ZEMTTalents

internal interface ZEMTTalentsRepository {
    suspend fun getTalents(collaboratorId: String): ZEMTResult<ZEMTTalents, ZEMTDataError>

    suspend fun getTalentsCompletedByList(collaboratorIdList: List<String>): ZEMTResult<List<ZEMTCollaboratorTalentStatus>, ZEMTDataError>
}