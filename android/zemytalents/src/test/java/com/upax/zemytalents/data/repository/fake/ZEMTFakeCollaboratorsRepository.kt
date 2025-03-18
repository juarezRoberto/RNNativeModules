package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.repositories.ZEMTCollaboratorsRepository

class ZEMTFakeCollaboratorsRepository(
    private val collaborators: List<ZEMTCollaboratorInCharge>
): ZEMTCollaboratorsRepository {

    override suspend fun findCollaboratorsByName(name: String): List<ZEMTCollaboratorInCharge> {
        return collaborators.filter { it.name.contains(name) }
    }

}