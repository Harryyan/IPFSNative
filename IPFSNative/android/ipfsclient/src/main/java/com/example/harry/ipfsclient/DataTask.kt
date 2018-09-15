package com.example.harry.ipfsclient

import android.os.AsyncTask
import android.util.Log
import io.ipfs.kotlin.IPFS
import java.io.File


class DataTask(private val filePath: String,
               private val callBack: ((HashMap<String?, String?>) -> Unit)) :
        AsyncTask<Void, Void, HashMap<String?, String?>>() {

    private val baseUrl = "http://10.0.2.2:5001/api/v0/"

    lateinit var file: File

    var shouldCancel: Boolean = false

    override fun onPreExecute() {
        super.onPreExecute()

        val filePath = "$filePath/Test.txt"
        file = File(filePath)
        val isNewFileCreated: Boolean = file.createNewFile()
        file.writeText("Hello world!")

        if (isNewFileCreated) {
            println("test is created successfully.")
        } else {
            println("test already exists.")
        }
    }

    override fun doInBackground(vararg p0: Void?): HashMap<String?, String?> {
        val apiClient = IPFS(base_url = baseUrl)
        val hashValue = apiClient.add.file(file, filename = "Text.txt").Hash
        val content = apiClient.get.cat(hashValue)

        Thread.sleep(7000)

        return hashMapOf("hashKey" to hashValue, "contentKey" to content)
    }

    override fun onPostExecute(result: HashMap<String?, String?>?) {
        super.onPostExecute(result)

        result?.let {
            if (!shouldCancel) {
                Log.d("####", "post e")
                callBack(result)
            }
        }
    }
}