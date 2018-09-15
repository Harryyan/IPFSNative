package com.example.harry.ipfsandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.example.harry.nativemodules.UploadReactPackage
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.shell.MainReactPackage
import java.util.*

class ReactActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler {

    private val moduleName = "Home"

    private var reactRootView: ReactRootView? = null
    private var reactInstanceManager: ReactInstanceManager? = null

    private val packages: List<ReactPackage>
        get() = Arrays.asList<ReactPackage>(
                MainReactPackage(),
                UploadReactPackage()
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRNRootView()
    }

    override fun onPause() {
        super.onPause()

        if (reactInstanceManager != null) {
            reactInstanceManager!!.onHostPause(this)
        }
    }

    override fun onResume() {
        super.onResume()

        if (reactInstanceManager != null) {
            reactInstanceManager!!.onHostResume(this, this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (reactInstanceManager != null) {
            reactInstanceManager!!.onHostDestroy(this)
        }
    }

    override fun onBackPressed() {
        if (reactInstanceManager != null) {
            reactInstanceManager!!.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU && reactInstanceManager != null) {
            reactInstanceManager!!.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    private fun initRNRootView() {
        reactRootView = ReactRootView(this)
        reactInstanceManager = ReactInstanceManager.builder()
                .setApplication(application)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(packages)
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build()
        reactRootView!!.startReactApplication(reactInstanceManager, moduleName, null)

        setContentView(reactRootView)
    }
}