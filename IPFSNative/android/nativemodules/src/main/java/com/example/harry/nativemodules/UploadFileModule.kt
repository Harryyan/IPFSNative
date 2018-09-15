package com.example.harry.nativemodules

import android.content.Context
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.lang.Exception


class UploadFileModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private val mContext: Context

    init {
        mContext = reactContext
    }

    override fun getName(): String {
        return "UploadFileModule"
    }

    @ReactMethod
    fun uploadFileWithCallBack(success: Callback, error: Callback) {
        try {
            success.invoke("correct!")
        } catch (e: Exception) {
            error.invoke("cao")
        }
    }
}