package com.upax.zemytalents.domain.models.modules

import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId

internal abstract class ZEMTAnswer(
    val id: ZEMTAnswerId,
    val questionId: ZEMTQuestionId,
    val date: String,
    val location: ZEMTLocation
) {

    abstract val position: Int

}