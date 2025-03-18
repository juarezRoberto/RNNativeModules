package com.awesomeproject

import android.content.Intent
import android.util.Log
import com.example.mylib.MyLibMainActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule

class FirstModule internal constructor(context: ReactApplicationContext) :
    ReactContextBaseJavaModule(context) {
    private var eventCount = 0

    override fun getName(): String {
        return FirstModule::class.java.simpleName
    }

    @ReactMethod
    fun hi() {
        Log.d("FirstModule", "hi")
    }

    @ReactMethod
    fun openModule() {
        Log.d("FirstModule", "openModule")
        val intent = Intent(reactApplicationContext, MyLibMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        reactApplicationContext.startActivity(intent)
    }

    @ReactMethod
    fun createCounterEvent(callback: Callback) {
        Log.d("FirstModule", "createCounterEvent")
        callback.invoke("return some data type from callback ")
    }

    @ReactMethod
    fun createCounterPromise(promise: Promise) {
        eventCount += 1
        try {
            promise.resolve("Data returned from promise")
            val params = Arguments.createMap().apply {
                putInt("eventProperty", eventCount)
            }
            sendEvent(reactApplicationContext, "EventCount", params)
        } catch (e: Exception) {
            promise.reject(e)
        }
    }

    private fun sendEvent(reactContext: ReactContext, eventName: String, params: WritableMap?) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }
}