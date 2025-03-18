package com.upax.zemytalents.ui.mytalents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.repositories.ZEMTModulesRepository
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import com.upax.zemytalents.domain.usecases.ZEMTGetUserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTMyTalentsViewModel(
    private val getUserDataUseCase: ZEMTGetUserDataUseCase,
    private val talentsRepository: ZEMTTalentsRepository,
    private val moduleRepository: ZEMTModulesRepository,
) : ViewModel() {

    private val _serviceState: MutableStateFlow<ZEMTMyTalentsServiceUiState> =
        MutableStateFlow(ZEMTMyTalentsServiceUiState.Idle)
    val serviceState = _serviceState.asStateFlow()

    val modules = moduleRepository.collectModules()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val user = getUserDataUseCase.invoke().stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun getMyTalents() = viewModelScope.launch {
        _serviceState.update { ZEMTMyTalentsServiceUiState.Loading }

        when (val result = talentsRepository.getTalents(getUserDataUseCase.getCollaboratorId())) {
            is ZEMTResult.Error -> _serviceState.update { ZEMTMyTalentsServiceUiState.Error }
            is ZEMTResult.Success -> _serviceState.update {
                ZEMTMyTalentsServiceUiState.Success(
                    ZEMTMyTalentsUiState(
                        user = getUserDataUseCase.getUser(),
                        talents = result.data
                    )
                )
            }
        }
    }
}