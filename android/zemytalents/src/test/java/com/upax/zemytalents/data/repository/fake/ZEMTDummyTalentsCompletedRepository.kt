package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.repositories.ZEMTTalentsCompletedRepository

internal class ZEMTDummyTalentsCompletedRepository: ZEMTTalentsCompletedRepository {

    var completeTalentsCalled = false
    var collaboratorIdCalled: String? = null

    override suspend fun completeTalents(collaboratorId: String) {
        completeTalentsCalled = true
        collaboratorIdCalled = collaboratorId
    }

}