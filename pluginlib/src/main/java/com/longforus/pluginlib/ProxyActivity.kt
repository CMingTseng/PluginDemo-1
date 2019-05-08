package com.longforus.pluginlib

import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class ProxyActivity: FragmentActivity(){

    var clientActivity:IPlugin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val className = intent.getStringExtra("className")
        val loadClass = PluginManager.pluginAPK?.classLoader?.loadClass(className) ?: throw RuntimeException("need load apk first")
        val instance = loadClass.newInstance()
        if (instance is IPlugin) {
            clientActivity = instance
            instance.onAttach(this)
            val bundle = Bundle()
            bundle.putBoolean("isInternal",false)
            instance.onCreate(bundle)
        } else {
            throw RuntimeException("target activity need implement IPlugin")
        }
    }


    override fun onStart() {
        super.onStart()
        clientActivity?.onStart()
    }

    override fun onRestart() {
        super.onRestart()
        clientActivity?.onRestart()
    }

    override fun onResume() {
        super.onResume()
        clientActivity?.onResume()
    }

    override fun onPause() {
        super.onPause()
        clientActivity?.onPause()
    }

    override fun onStop() {
        super.onStop()
        clientActivity?.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
        clientActivity?.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        clientActivity?.onActivityResult(requestCode, resultCode, data)
    }

    override fun getAssets(): AssetManager {
        return PluginManager.pluginAPK?.assetManager ?: super.getAssets()
    }


    override fun getResources(): Resources {
        return PluginManager.pluginAPK?.resources ?: super.getResources()
    }


    override fun getClassLoader(): ClassLoader {
        return PluginManager.pluginAPK?.classLoader ?: super.getClassLoader()
    }
}