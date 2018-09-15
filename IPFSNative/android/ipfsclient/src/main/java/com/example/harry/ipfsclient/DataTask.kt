package com.example.harry.ipfsclient

import android.os.AsyncTask
import android.util.Log
import io.ipfs.kotlin.IPFS
import java.io.File


class DataTask(private val filePath: String,
               private val callBack: ((HashMap<String?, String?>) -> Unit)) :
        AsyncTask<Void, Void, HashMap<String?, String?>>() {

    private val baseUrl = "http://10.0.2.2:5001/api/v0/"
    private val fakeData = "The Sylo Protocol acts as the confidential networking layer for all " +
            "integrated decentralised applications, creating peer-to-peer (P2P) connections and " +
            "providing an efficient way for users to interact and exchange data confidentially " +
            "over the network. The Sylo Protocol consists of client-side APIs and services that " +
            "allow Connected Applications to confidentially perform communication functions with " +
            "other users on the network. \n\nSylo Signalling will be a decentralised service run by " +
            "resources on the Sylo Network. Sylo Signalling will provide the ability for peers " +
            "to connect and is used to send messages and connectivity requests to enable reliable" +
            " communication. Sylo Signalling Nodes will be remunerated in SYLOs in exchange " +
            "for providing this service to the network."

    lateinit var file: File

    var shouldCancel: Boolean = false

    override fun onPreExecute() {
        super.onPreExecute()

        val filePath = "$filePath/Test.txt"
        file = File(filePath)
        val isNewFileCreated: Boolean = file.createNewFile()
        file.writeText(fakeData)

        if (isNewFileCreated) {
            println("file is created successfully.")
        } else {
            println("file already exists.")
        }
    }

    override fun doInBackground(vararg p0: Void?): HashMap<String?, String?> {
        val apiClient = IPFS(base_url = baseUrl)
        val hashValue = apiClient.add.file(file, filename = "Text.txt").Hash
        val content = apiClient.get.cat(hashValue)

        return hashMapOf("hashKey" to hashValue, "contentKey" to content)
    }

    override fun onPostExecute(result: HashMap<String?, String?>?) {
        super.onPostExecute(result)

        result?.let {
            if (!shouldCancel) {
                callBack(result)
            }
        }
    }
}