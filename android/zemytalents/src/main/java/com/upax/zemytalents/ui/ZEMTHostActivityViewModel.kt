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
import org.json.JSONObject

class ZEMTHostActivityViewModel(private val app: Application) : AndroidViewModel(app) {

    private var _onSuccess = MutableStateFlow<Boolean>(false)
    val onSuccess = _onSuccess.asStateFlow()


    fun configureEnvironment(userJson: String) = viewModelScope.launch {
        Log.e("REACT", "startingServiceCoordinator")
        val authUserUseCase = ZCSCExpose.getAuthenticateUserV2UseCase(app.applicationContext)
        val (zeusId, password) = getCredentials(userJson)
        val result = authUserUseCase(zeusId, password)
        if (result.success) {
            loadMockUser(userJson)
            _onSuccess.update { true }
        } else {
            _onSuccess.update { false }
        }
    }

    private suspend fun loadMockUser(userJson: String) {
        try {
            val session = ZCSISession(sessionStatus = ZCSISessionStatus.VERIFIED.code)
            val userJsonObject = JSONObject(userJson)
            val companyJsonObject = userJsonObject.getJSONObject("company")

            val company = ZCSICompany(
                companyId = companyJsonObject.getString("companyId"),
                name = companyJsonObject.getString("name"),
                primaryColor = companyJsonObject.getString("primaryColor")
            )
            val user = ZCSIUser(
                zeusId = userJsonObject.getString("zeusId"),
                employeeNumber = userJsonObject.getString("employeeNumber"),
                name = userJsonObject.getString("name"),
                lastName = userJsonObject.getString("lastName"),
                secondLastName = userJsonObject.getString("secondLastName"),
                companyIdentifier = company.companyId
            )

            val sessionInfo = ZCSIExpose.getSessionInfo(app.applicationContext)
            sessionInfo.saveUser(user)
            sessionInfo.saveCompany(user.zeusId, company)
            sessionInfo.saveSession(user.zeusId, session)
            ZCDSApplicationController.setPrimaryColor(company.primaryColor)
        } catch (e: Exception) {
            Log.e("REACT", "MyTalentsModule - failed loadMockUser ${e.message}")
        }
    }

    private fun getCredentials(userJson: String): Pair<String, String> {
        val userJsonObject = JSONObject(userJson)
        val companyJsonObject = userJsonObject.getJSONObject("credentials")
        val zeusId = companyJsonObject.getString("zeusId")
        val password = companyJsonObject.getString("password")
        return Pair(zeusId, password)
    }

}