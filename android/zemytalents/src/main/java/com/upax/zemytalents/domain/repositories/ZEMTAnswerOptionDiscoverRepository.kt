package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId

internal interface ZEMTAnswerOptionDiscoverRepository {

    suspend fun getAnswerById(answerId: ZEMTAnswerId): ZEMTAnswerOptionDiscover

}