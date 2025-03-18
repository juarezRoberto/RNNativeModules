package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.remote.responses.ZEMTModuleMultimediaResponse
import com.upax.zemytalents.domain.models.modules.ZEMTModuleMultimedia
import com.upax.zemytalents.domain.models.modules.ZEMTModuleMultimediaType

internal fun List<ZEMTModuleMultimediaResponse>.toDomainModels(): List<ZEMTModuleMultimedia> {
    return this.map { it.toDomainModel() }
}

internal fun ZEMTModuleMultimediaResponse.toDomainModel(): ZEMTModuleMultimedia {
    return ZEMTModuleMultimedia(
        url = this.url.orEmpty(),
        type = ZEMTModuleMultimediaType.fromId(this.type.orZero()),
        order = this.order.orZero(),
        title = this.title.orEmpty(),
        duration = this.duration.orEmpty(),
        description = this.description.orEmpty(),
        urlThumbnail = this.urlThumbnail.orEmpty()
    )

}