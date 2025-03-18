package com.upax.zemytalents.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.data.local.preferences.ZEMTLocalPreferences
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTModulesRepository
import com.upax.zemytalents.domain.usecases.ZEMTCaptionsDownloader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTStartViewModel(
    private val moduleRepository: ZEMTModulesRepository,
    private val localPreferences: ZEMTLocalPreferences,
    private val captionsDownloader: ZEMTCaptionsDownloader
) : ViewModel() {

    private var _uiState: MutableStateFlow<ZEMTStartUiModel> =
        MutableStateFlow(ZEMTStartUiModel.Idle)
    val uiState = _uiState.asStateFlow()

    fun getModules() = viewModelScope.launch {
        _uiState.update { ZEMTStartUiModel.Loading }
        when (val result = moduleRepository.getModules()) {
            is ZEMTResult.Error -> _uiState.update {
                ZEMTStartUiModel.Error
            }

            is ZEMTResult.Success -> _uiState.update {
                ZEMTStartUiModel.Success(result.data, localPreferences.wasIntroductionViewed)
            }
        }
    }

    fun downloadCaptions() = viewModelScope.launch {
        captionsDownloader.download().collect { result ->
            when (result) {
                is ZEMTConversationResult.Error -> _uiState.update { ZEMTStartUiModel.ErrorCaptions }
                is ZEMTConversationResult.Success -> _uiState.update { ZEMTStartUiModel.SuccessCaptions }
                is ZEMTConversationResult.Loading -> _uiState.update { ZEMTStartUiModel.Loading }
                else -> Unit
            }
        }
    }

}