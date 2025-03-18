package com.upax.zemytalents.common

import com.upax.zcinterceptor.environment.ZCIEnvironment

private const val BASE_URL_NATIVE_LIB = "zemt-base-url-native-lib"
private const val FIREBASE_NATIVE_LIB = "zemt-firebase-native-lib"

object ZEMTEnvironment : ZCIEnvironment() {

    init {
        System.loadLibrary(BASE_URL_NATIVE_LIB)
        System.loadLibrary(FIREBASE_NATIVE_LIB)
    }


    private external fun getCustomTalentsUrl(
        environment: String,
        useAlternativeUrl: Boolean
    ): String

    fun getCustomTalentsUrl(useAlternativeUrl: Boolean = false): String =
        getCustomTalentsUrl(getAppEnvironment(), useAlternativeUrl)

}