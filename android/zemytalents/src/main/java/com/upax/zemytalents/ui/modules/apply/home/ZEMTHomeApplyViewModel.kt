package com.upax.zemytalents.ui.modules.apply.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.domain.repositories.ZEMTModulesRepository
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyApplyProgressUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

internal class ZEMTHomeApplyViewModel(
    moduleRepository: ZEMTModulesRepository,
    surveyApplyProgressUseCase: ZEMTGetSurveyApplyProgressUseCase
) : ViewModel() {

    val modulesState = combine(
        moduleRepository.collectModules(),
        surveyApplyProgressUseCase.invoke()
    ) { modules, progress ->
        modules.map {
            when(it.stage) {
                ZEMTModuleStage.UNKNOWN -> it
                ZEMTModuleStage.DISCOVER -> it.copy(progress = 1f)
                ZEMTModuleStage.CONFIRM -> it.copy(progress = 1f)
                ZEMTModuleStage.APPLY -> it.copy(progress = progress)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}