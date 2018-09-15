package com.example.harry.ipfsclient

import android.content.Context
import android.os.AsyncTask

class IPFSClient(private val context: Context) {

    var dataTask: DataTask? = null

    fun uploadFile(callBack: ((HashMap<String?, String?>) -> Unit)) {
        dataTask?.let {
            if (it.status != AsyncTask.Status.FINISHED) {
                it.cancel(true)
                it.shouldCancel = true
            }

            dataTask = DataTask(context.filesDir.path.toString(), callBack).apply { execute() }
        }
    }
}