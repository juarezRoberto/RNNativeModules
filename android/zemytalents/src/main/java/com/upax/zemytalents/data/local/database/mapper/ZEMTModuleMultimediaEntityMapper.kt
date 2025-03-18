package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleMultimediaEntity
import com.upax.zemytalents.domain.models.modules.ZEMTModuleMultimedia
import com.upax.zemytalents.domain.models.modules.ZEMTModuleMultimediaType
import com.upax.zemytalents.ui.shared.models.ZEMTModuleMultimediaUiModel


internal fun List<ZEMTModuleMultimedia>.toEntityModels(moduleId: Int): List<ZEMTModuleMultimediaEntity> {
    return this.map { it.toEntity(moduleId) }
}

internal fun ZEMTModuleMultimedia.toEntity(moduleId: Int): ZEMTModuleMultimediaEntity {
    return ZEMTModuleMultimediaEntity(
        moduleId = moduleId,
        url = this.url,
        type = this.type.name,
        order = this.order,
        title = this.title,
        duration = this.duration,
        description = this.description,
        urlThumbnail = this.urlThumbnail
    )
}

internal fun List<ZEMTModuleMultimediaEntity>.toUiModels(): List<ZEMTModuleMultimediaUiModel> {
    return this.map { it.toUiModel() }
}

internal fun ZEMTModuleMultimediaEntity.toUiModel(): ZEMTModuleMultimediaUiModel {
    return ZEMTModuleMultimediaUiModel(
        url = this.url,
        type = ZEMTModuleMultimediaType.valueOf(this.type),
        order = this.order,
        title = this.title,
        duration = this.duration,
        description = this.description,
        urlThumbnail = this.urlThumbnail
    )
}