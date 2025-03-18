package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerSavedDiscover

internal interface ZEMTAnswerSavedDiscoverRepository {

    suspend fun getTotalNeutralAnswers(): Int

    suspend fun saveAnswer(answer: ZEMTAnswerOptionDiscover)

    suspend fun getAnswers(): List<ZEMTAnswerSavedDiscover>

    suspend fun deleteAll()

}