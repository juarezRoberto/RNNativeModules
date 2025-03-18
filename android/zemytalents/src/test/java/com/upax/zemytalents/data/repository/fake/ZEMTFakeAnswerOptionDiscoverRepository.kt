package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionMother
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.repositories.ZEMTAnswerOptionDiscoverRepository

internal class ZEMTFakeAnswerOptionDiscoverRepository(
    var answerToReturn: ZEMTAnswerOptionDiscover = ZEMTAnswerOptionMother.random()
) : ZEMTAnswerOptionDiscoverRepository {

    override suspend fun getAnswerById(answerId: ZEMTAnswerId): ZEMTAnswerOptionDiscover {
        return answerToReturn
    }
}