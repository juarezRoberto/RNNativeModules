package com.upax.zemytalents.domain.models.modules

internal data class ZEMTModule(
    val moduleId: Int,
    val surveyId: String,
    val name: String,
    val order: Int,
    val multimedia: List<ZEMTModuleMultimedia>,
    val description: String,
    val isComplete: Boolean,
    val stage: ZEMTModuleStage
)

internal data class ZEMTModuleMultimedia(
    val url: String,
    val type: ZEMTModuleMultimediaType,
    val order: Int,
    val title: String,
    val duration: String,
    val description: String,
    val urlThumbnail: String
)