package com.example.harry.nativemodules

import android.content.Context
import com.example.harry.ipfsclient.IPFSClient
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
        val apiClient = IPFSClient(mContext)
        apiClient.uploadFile {
            val hash = it["hashKey"]
            val content = it["contentKey"]

            try {
                success.invoke(hash, content)
            } catch (e: Exception) {
                error.invoke(null)
            }
        }
    }
}