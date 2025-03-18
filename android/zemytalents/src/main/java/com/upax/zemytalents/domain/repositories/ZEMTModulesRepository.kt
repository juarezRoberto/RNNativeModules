package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.modules.ZEMTModule
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel
import kotlinx.coroutines.flow.Flow

internal interface ZEMTModulesRepository {

    suspend fun getModules(): ZEMTResult<List<ZEMTModule>, ZEMTDataError>

    fun collectModules(): Flow<List<ZEMTModuleUiModel>>
}