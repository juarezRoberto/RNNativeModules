package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerSavedDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerSavedMother
import com.upax.zemytalents.domain.repositories.ZEMTAnswerSavedDiscoverRepository
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal class ZEMTFakeAnswerSavedDiscoverRepository(
    var totalNeutralAnswer: Int = ZEMTRandomValuesUtil.getInt(),
    var answersToReturn: List<ZEMTAnswerSavedDiscover> = ZEMTRandomValuesUtil.getRandomIntRange().map {
        ZEMTAnswerSavedMother.random()
    }
): ZEMTAnswerSavedDiscoverRepository {

    var answersDeleted = false

    override suspend fun getTotalNeutralAnswers(): Int = totalNeutralAnswer

    override suspend fun saveAnswer(answer: ZEMTAnswerOptionDiscover) { }

    override suspend fun getAnswers(): List<ZEMTAnswerSavedDiscover> = answersToReturn

    override suspend fun deleteAll() {
        answersDeleted = true
    }
}