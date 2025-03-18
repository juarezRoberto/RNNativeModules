package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orFalse
import com.upax.zemytalents.common.orOne
import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.remote.responses.ZEMTModuleResponse
import com.upax.zemytalents.domain.models.modules.ZEMTModule
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage

internal fun List<ZEMTModuleResponse>.toDomainModels(): List<ZEMTModule> {
    return this.mapIndexed { index, item -> item.toDomainModel(index) }
}

internal fun ZEMTModuleResponse.toDomainModel(index: Int): ZEMTModule {
    val moduleType = when (index) {
        0 -> ZEMTModuleStage.DISCOVER
        1 -> ZEMTModuleStage.CONFIRM
        2 -> ZEMTModuleStage.APPLY
        else -> ZEMTModuleStage.UNKNOWN
    }

    return ZEMTModule(
        moduleId = this.moduleId.orZero(),
        surveyId = this.surveyId.orOne().toString(),
        name = this.name.orEmpty(),
        order = this.order.orZero(),
        multimedia = this.multimedia.orEmpty().toDomainModels(),
        description = this.description.orEmpty(),
        isComplete = this.isComplete.orFalse(),
        stage = moduleType
    )
}
