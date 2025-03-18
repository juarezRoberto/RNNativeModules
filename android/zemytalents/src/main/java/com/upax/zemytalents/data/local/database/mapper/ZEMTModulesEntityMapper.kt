package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleEntity
import com.upax.zemytalents.domain.models.modules.ZEMTModule
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.ui.shared.models.ZEMTModuleMultimediaUiModel
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel

internal fun List<ZEMTModule>.toEntityModels(): List<ZEMTModuleEntity> {
    return this.map { it.toEntity() }
}

internal fun ZEMTModule.toEntity(): ZEMTModuleEntity {
    return ZEMTModuleEntity(
        moduleId = this.moduleId,
        surveyId = this.surveyId,
        name = this.name,
        order = this.order,
        description = this.description,
        isComplete = this.isComplete,
        stage = this.stage.name
    )
}

internal fun ZEMTModuleEntity.toUiModel(multimediaUiModels: List<ZEMTModuleMultimediaUiModel>): ZEMTModuleUiModel {
    return ZEMTModuleUiModel(
        moduleId = this.moduleId,
        surveyId = this.surveyId,
        name = this.name,
        order = this.order,
        moduleDesc = this.description,
        isComplete = this.isComplete,
        progress = 0.01f,
        stage = ZEMTModuleStage.valueOf(this.stage),
        multimedia = multimediaUiModels
    )
}