package com.upax.zemytalents.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zcdesignsystem.expose.ZCDSApplicationController
import com.upax.zcservicecoordinator.expose.ZCSCExpose
import com.upax.zcsessioninfo.domain.model.ZCSICompany
import com.upax.zcsessioninfo.domain.model.ZCSISession
import com.upax.zcsessioninfo.domain.model.ZCSISessionStatus
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zcsessioninfo.expose.ZCSIExpose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ZEMTHostActivityViewModel(private val app: Application) : AndroidViewModel(app) {

    private var _onSuccess = MutableStateFlow<Boolean>(false)
    val onSuccess = _onSuccess.asStateFlow()


    fun configureEnvironment() = viewModelScope.launch {
        Log.e("REACT", "startingServiceCoordinator")
        val authUserUseCase = ZCSCExpose.getAuthenticateUserV2UseCase(app.applicationContext)
        val result = authUserUseCase("4b1bc2b6-7c97-4de8-a9d9-0f2f9cf519b2", "GrupoSalinas2024")
        if (result.success) {
            loadMockUser()
            _onSuccess.update { true }
        } else {
            _onSuccess.update { false }
//            Toast.makeText(this@ZEMTHostActivity, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun loadMockUser() {
        val session = ZCSISession(sessionStatus = ZCSISessionStatus.VERIFIED.code)
        val company = ZCSICompany(
            companyId = "310",
            name = "Grupo Nach",
            primaryColor = "#00C5FF"
        )
        val user = ZCSIUser(
            zeusId = "ba3e85c2-a82e-49b3-90d3-b2740eac2193",
            employeeNumber = "999956281",
            name = "David Arturo",
            lastName = "Martinez",
            secondLastName = "Guzman",
            companyIdentifier = company.companyId
        )

        val sessionInfo = ZCSIExpose.getSessionInfo(app.applicationContext)
        sessionInfo.saveUser(user)
        sessionInfo.saveCompany(user.zeusId, company)
        sessionInfo.saveSession(user.zeusId, session)
        ZCDSApplicationController.setPrimaryColor(company.primaryColor)
    }

}