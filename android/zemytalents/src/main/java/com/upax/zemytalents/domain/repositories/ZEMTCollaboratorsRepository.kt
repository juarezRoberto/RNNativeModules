package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge

internal interface ZEMTCollaboratorsRepository {

    suspend fun findCollaboratorsByName(name: String): List<ZEMTCollaboratorInCharge>

}