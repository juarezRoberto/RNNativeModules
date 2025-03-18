package com.upax.zemytalents.ui.modules.confirm.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.domain.repositories.ZEMTModulesRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyConfirmRepository
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import com.upax.zemytalents.domain.usecases.ZEMTGetUserDataUseCase
import com.upax.zemytalents.ui.modules.confirm.home.models.ZEMTHomeConfirmServiceState
import com.upax.zemytalents.ui.modules.confirm.home.models.ZEMTHomeConfirmUiState
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTHomeConfirmViewModel(
    private val getUserDataUseCase: ZEMTGetUserDataUseCase,
    private val moduleRepository: ZEMTModulesRepository,
    private val talentsRepository: ZEMTTalentsRepository,
    private val dbSurveyConfirmRepository: ZEMTSurveyConfirmRepository,
) : ViewModel() {

    private val _serviceState: MutableStateFlow<ZEMTHomeConfirmServiceState> =
        MutableStateFlow(ZEMTHomeConfirmServiceState.Idle)
    val serviceState = _serviceState.asStateFlow()

    val modulesWithProgressState: Flow<List<ZEMTModuleUiModel>> = combine(
        dbSurveyConfirmRepository.collectTotalQuestionsAnswered(),
        moduleRepository.collectModules(),
    ) { progress, modules ->
        modules.map {
            when (it.stage) {
                ZEMTModuleStage.UNKNOWN -> it
                ZEMTModuleStage.DISCOVER -> it.copy(progress = 1f)
                ZEMTModuleStage.CONFIRM -> it.copy(progress = progress)
                ZEMTModuleStage.APPLY -> it.copy(progress = 0.01f)
            }
        }
    }

    fun getTalents() = viewModelScope.launch {
        _serviceState.update { ZEMTHomeConfirmServiceState.Loading }
        when (val result = talentsRepository.getTalents(getUserDataUseCase.getCollaboratorId())) {
            is ZEMTResult.Error -> {
                _serviceState.update { ZEMTHomeConfirmServiceState.Error }
            }

            is ZEMTResult.Success -> {
                _serviceState.update {
                    ZEMTHomeConfirmServiceState.Success(
                        ZEMTHomeConfirmUiState(
                            getUserDataUseCase.getUser(),
                            result.data
                        )
                    )
                }
            }
        }
    }
}