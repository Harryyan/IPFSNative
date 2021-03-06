package com.example.harry.ipfsandroid

import android.app.Application
import com.example.harry.nativemodules.UploadReactPackage
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.shell.MainReactPackage
import com.facebook.soloader.SoLoader
import java.util.*

class MainApplication : Application(), ReactApplication {

    private val mReactNativeHost = object : ReactNativeHost(this) {

        override fun getUseDeveloperSupport(): Boolean {
            return BuildConfig.DEBUG
        }

        public override fun getPackages(): List<ReactPackage> {
            return Arrays.asList(
                    MainReactPackage(),
                    UploadReactPackage()
            )
        }
    }

    override fun getReactNativeHost(): ReactNativeHost {
        return mReactNativeHost
    }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, /* native exopackage */ false)
    }
}