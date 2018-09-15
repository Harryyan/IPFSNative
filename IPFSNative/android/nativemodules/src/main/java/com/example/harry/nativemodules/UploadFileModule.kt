package com.example.harry.nativemodules

import android.content.Context
import com.example.harry.ipfsclient.IPFSClient
import com.facebook.react.bridge.*
import java.lang.Exception


class UploadFileModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private val mContext: Context
    private val moduleName = "UploadFileModule"

    init {
        mContext = reactContext
    }

    override fun getName(): String {
        return moduleName
    }

    @ReactMethod
    fun uploadFileWithCallBack(success: Callback, error: Callback) {
        val apiClient = IPFSClient(mContext)
        apiClient.uploadFile {
            try {
                val reactMap = Arguments.createMap()
                reactMap.putString("hash", it["hashKey"])
                reactMap.putString("content", it["contentKey"])

                success.invoke(reactMap)
            } catch (e: Exception) {
                error.invoke(e.message)
            }
        }
    }
}