package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.local.preferences.ZEMTLocalPreferences

internal class ZEMTClearLocalDataUseCase(
    private val clearDatabaseUseCase: ZEMTClearDatabaseUseCase,
    private val localPreferences: ZEMTLocalPreferences
) {
    suspend operator fun invoke() {
        clearDatabaseUseCase.invoke()
        localPreferences.clearAll()
    }
}