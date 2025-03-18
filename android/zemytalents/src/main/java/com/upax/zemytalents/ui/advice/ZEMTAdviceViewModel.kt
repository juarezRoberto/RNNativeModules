package com.upax.zemytalents.ui.advice

import androidx.lifecycle.ViewModel
import com.upax.zemytalents.data.local.preferences.ZEMTLocalPreferences

internal class ZEMTAdviceViewModel(
    private val localPreferences: ZEMTLocalPreferences
) : ViewModel() {

    fun saveViewedIntroduction() {
        localPreferences.wasIntroductionViewed = true
    }
}