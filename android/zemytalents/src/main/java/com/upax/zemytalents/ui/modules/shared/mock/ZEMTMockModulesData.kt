package com.upax.zemytalents.ui.modules.shared.mock

import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel

internal object ZEMTMockModulesData {
    fun getModules(): List<ZEMTModuleUiModel> {
        return listOf(
            ZEMTModuleUiModel(
                moduleId = 1,
                stage = ZEMTModuleStage.DISCOVER,
                moduleDesc = "Lleva tu potencial y tus fortalezas al máximo.",
                order = 1,
                isComplete = false,
                progress = 0f,
                name = "Descubre",
                multimedia = emptyList(),
                surveyId = "3973"
            ),
            ZEMTModuleUiModel(
                moduleId = 2,
                stage = ZEMTModuleStage.CONFIRM,
                name = "Confirma",
                moduleDesc = "Lleva tu potencial y tus fortalezas al máximo.",
                order = 2,
                progress = 0f,
                isComplete = false,
                surveyId = "7060",
                multimedia = emptyList()
            ),
            ZEMTModuleUiModel(
                moduleId = 3,
                stage = ZEMTModuleStage.APPLY,
                moduleDesc = "Lleva tu potencial y tus fortalezas al máximo.",
                order = 3,
                progress = 0f,
                isComplete = false,
                name = "Aplica",
                surveyId = "7091",
                multimedia = emptyList()
            )
        )
    }

    fun getTalents(): List<ZEMTTalent> {
        return listOf(
            ZEMTTalent(
                id = 1,
                name = "ENCANTO",
                description = "lorem ipsum",
                isTempTalent = true
            ),
            ZEMTTalent(
                id = 2,
                name = "COMUNICADOR",
                description = "lorem ipsum",
                isTempTalent = true,
            ),
            ZEMTTalent(
                id = 3,
                name = "CONFIDENTE",
                description = "lorem ipsum",
                isTempTalent = true
            ),
            ZEMTTalent(
                id = 4,
                name = "RECONSTRUCTOR",
                description = "lorem ipsum",
                isTempTalent = true
            ),
            ZEMTTalent(
                id = 5,
                name = "INCLUYENTE",
                description = "lorem ipsum",
                isTempTalent = true,
            ),
            ZEMTTalent(
                id = 5,
                name = "EQUITATIVO",
                description = "lorem ipsum",
                isTempTalent = true,
            )
        )
    }
}