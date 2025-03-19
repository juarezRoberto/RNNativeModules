package com.upax.zemytalents.expose

import android.content.Context
import android.content.Intent
import com.upax.zemytalents.di.ZEMTUseCaseProvider
import com.upax.zemytalents.ui.ZEMTHostActivity

object ZEMTMyTalentsExpose {

    @Deprecated(message = "Only for development, Use Interceptor on generic app")
    fun openModule(context: Context, userJson: String) {
        val intent = Intent(context, ZEMTHostActivity::class.java)
        intent.putExtra("JSON", userJson)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    suspend fun clearLocalData(context: Context) {
        ZEMTUseCaseProvider.provideClearLocalDataUseCase(context).invoke()
    }
}