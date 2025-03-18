package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.conversations.dao.ZEMTCollaboratorInChargeDao
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.repositories.ZEMTCollaboratorsRepository

internal class ZEMTRoomCollaboratorsRepository(
    private val dao: ZEMTCollaboratorInChargeDao
): ZEMTCollaboratorsRepository {

    override suspend fun findCollaboratorsByName(name: String): List<ZEMTCollaboratorInCharge> {
        val formattedQuery = "%${name.trim()}%"
        return dao.findCollaboratorsByName(formattedQuery).map { it.toModel() }
    }

}