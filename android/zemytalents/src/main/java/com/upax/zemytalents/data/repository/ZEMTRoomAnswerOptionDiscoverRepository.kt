package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTAnswerOptionDiscoverDao
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.domain.repositories.ZEMTAnswerOptionDiscoverRepository

internal class ZEMTRoomAnswerOptionDiscoverRepository(
    private val answerOptionDao: ZEMTAnswerOptionDiscoverDao,
) : ZEMTAnswerOptionDiscoverRepository {

    override suspend fun getAnswerById(answerId: ZEMTAnswerId): ZEMTAnswerOptionDiscover {
        val answerEntity = answerOptionDao.getAnswerOptionById(answerId.value)
        return ZEMTAnswerOptionDiscover(
            id = ZEMTAnswerId(answerEntity.answerOptionId),
            questionId = ZEMTQuestionId(answerEntity.questionId),
            order = answerEntity.order,
            text = answerEntity.text,
            value = answerEntity.value
        )
    }

}