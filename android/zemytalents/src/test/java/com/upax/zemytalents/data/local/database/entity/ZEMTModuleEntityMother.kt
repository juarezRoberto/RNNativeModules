package com.upax.zemytalents.data.local.database.entity

import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleEntity
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTModuleEntityMother {

    fun apply(
        moduleId: Int = ZEMTRandomValuesUtil.getInt(),
        surveyId: String = ZEMTRandomValuesUtil.getString(),
        name: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        description: String = ZEMTRandomValuesUtil.getString(),
        isComplete: Boolean = ZEMTRandomValuesUtil.getBoolean(),
        stage: String = ZEMTRandomValuesUtil.getString(),
    ): ZEMTModuleEntity {
        return ZEMTModuleEntity(
            moduleId = moduleId,
            surveyId = surveyId,
            name = name,
            order = order,
            description = description,
            isComplete = isComplete,
            stage = stage
        )
    }

    fun random() = apply()

}