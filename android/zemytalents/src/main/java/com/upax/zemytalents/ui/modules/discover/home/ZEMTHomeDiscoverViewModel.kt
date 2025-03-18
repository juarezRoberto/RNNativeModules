package com.upax.zemytalents.ui.modules.discover.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.repositories.ZEMTModulesRepository
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyDiscoverProgressUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetUserDataUseCase
import com.upax.zemytalents.ui.modules.discover.home.models.ZEMTHomeDiscoverUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class ZEMTHomeDiscoverViewModel(
    getUserDataUseCase: ZEMTGetUserDataUseCase,
    getProgressUseCase: ZEMTGetSurveyDiscoverProgressUseCase,
    private val moduleRepository: ZEMTModulesRepository
) : ViewModel() {

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState = _isLoadingState.asStateFlow()
    private val _navToDiscoverModuleState = MutableStateFlow<Boolean?>(null)
    val navToDiscoverModuleState = _navToDiscoverModuleState.asStateFlow()

    val uiState = combine(
        getUserDataUseCase.invoke(),
        getProgressUseCase.invoke(),
        moduleRepository.collectModules()
    ) { user, progress, modules ->
        ZEMTHomeDiscoverUiState(user, progress, modules)
    }.stateIn(viewModelScope, SharingStarted.Lazily, ZEMTHomeDiscoverUiState())

    fun setNavigateToDiscoverState() {
        _navToDiscoverModuleState.update { true }
    }

    fun resetNavigateToDiscoverState() {
        _navToDiscoverModuleState.update { false }
    }

}