package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTTalentsCompletedDao
import com.upax.zemytalents.data.local.database.entity.talentscompleted.ZEMTTalentsCompletedEntity
import com.upax.zemytalents.domain.repositories.ZEMTTalentsCompletedRepository

internal class ZEMTRoomTalentsCompletedRepository(
    private val dao: ZEMTTalentsCompletedDao
): ZEMTTalentsCompletedRepository {

    override suspend fun completeTalents(collaboratorId: String) {
        dao.insertTalentCompleted(
            ZEMTTalentsCompletedEntity(
                collaboratorId = collaboratorId,
                completed = true
            )
        )
    }

}