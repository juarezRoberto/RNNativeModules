package com.awesomeproject

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.upax.zemytalents.expose.ZEMTMyTalentsExpose

class MyTalentsModule internal constructor(private val context: ReactApplicationContext) :
    ReactContextBaseJavaModule(context) {

    override fun getName(): String = MyTalentsModule::class.java.simpleName

    @ReactMethod
    fun openModule() {
        Log.e("REACT", "MyTalentsModule - openModule")
        ZEMTMyTalentsExpose.openModule(context)
    }

    @ReactMethod
    fun startServiceCoordinator() {
        Log.e("REACT", "MyTalentsModule - startServiceCoordinator")
//        ZEMTMyTalentsExpose.startServiceCoordinator(context)
    }
}