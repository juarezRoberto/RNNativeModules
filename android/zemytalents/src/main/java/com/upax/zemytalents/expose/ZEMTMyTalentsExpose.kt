package com.upax.zemytalents.expose

import android.content.Context
import android.content.Intent
import android.util.Log
import com.upax.zcdesignsystem.expose.ZCDSApplicationController
import com.upax.zcservicecoordinator.expose.ZCSCExpose
import com.upax.zcsessioninfo.domain.model.ZCSICompany
import com.upax.zcsessioninfo.domain.model.ZCSISession
import com.upax.zcsessioninfo.domain.model.ZCSISessionStatus
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zcsessioninfo.expose.ZCSIExpose
import com.upax.zemytalents.di.ZEMTUseCaseProvider
import com.upax.zemytalents.ui.ZEMTHostActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object ZEMTMyTalentsExpose {

    @Deprecated(message = "Only for development, Use Interceptor on generic app")
    fun openModule(context: Context) {
        val intent = Intent(context, ZEMTHostActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun startServiceCoordinator(context: Context) {
        CoroutineScope(Job() + Dispatchers.Main).launch {
            val authUserUseCase = ZCSCExpose.getAuthenticateUserV2UseCase(context)
            val result =
                authUserUseCase.invoke("4b1bc2b6-7c97-4de8-a9d9-0f2f9cf519b2", "GrupoSalinas2024")
            if (result.success) {
                Log.e("REACT", "MyTalentsModule - success ServiceCoordinator")
            } else {
                Log.e("REACT", "MyTalentsModule - error ServiceCoordinator")
            }
        }
    }

    private suspend fun loadMockUser(context: Context) {
        val session = ZCSISession(sessionStatus = ZCSISessionStatus.VERIFIED.code)
        val company = ZCSICompany(
            "310",
            "Grupo Nach",
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

        val sessionInfo = ZCSIExpose.getSessionInfo(context)
        sessionInfo.saveUser(user)
        sessionInfo.saveCompany(user.zeusId, company)
        sessionInfo.saveSession(user.zeusId, session)
        ZCDSApplicationController.setPrimaryColor(company.primaryColor)
    }

    suspend fun clearLocalData(context: Context) {
        ZEMTUseCaseProvider.provideClearLocalDataUseCase(context).invoke()
    }
}