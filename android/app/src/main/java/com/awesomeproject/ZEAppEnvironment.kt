package com.awesomeproject

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.upax.zcinterceptor.environment.ZCIEnvironment
import com.upax.zcservicecoordinator.expose.models.ZCSCEnvironmentKey

private const val ZE_ENVIRONMENT_NATIVE_LIB = "ze-environment-native-lib"
private const val ZE_FIREBASE_NATIVE_LIB = "ze-firebase-native-lib"
private const val ZE_DEV = "DEV"
private const val ZE_QA = "QA"

class ZEAppEnvironment(private val context: Context) : ZCIEnvironment() {

    init {
        System.loadLibrary(ZE_ENVIRONMENT_NATIVE_LIB)
        System.loadLibrary(ZE_FIREBASE_NATIVE_LIB)
    }

    fun getEnvironment() = when (getAppEnvironment()) {
        ZE_DEV -> ZCSCEnvironmentKey.DEV
        ZE_QA -> ZCSCEnvironmentKey.QA
        else -> ZCSCEnvironmentKey.PROD
    }

    private external fun getAppFirebaseConfig(): String

    fun initializeFirebase() {
        if (firebaseAppExists()) {
            FirebaseApp.getInstance().delete()
        }
        FirebaseApp.initializeApp(context, getFirebaseOptions())
    }

    private fun firebaseAppExists(): Boolean {
        return FirebaseApp.getApps(context)
            .firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME } != null
    }

    private fun getFirebaseOptions(): FirebaseOptions {
        val firebaseConfig = getFirebaseConfigFromParams(getAppFirebaseConfig())
        return FirebaseOptions.Builder()
            .setApiKey(firebaseConfig.apikey)
            .setDatabaseUrl(firebaseConfig.databaseUrl)
            .setProjectId(firebaseConfig.projectId)
            .setStorageBucket(firebaseConfig.storageBucket)
            .setGcmSenderId(firebaseConfig.gcmSenderId)
            .setApplicationId(firebaseConfig.applicationId)
            .build()
    }
}