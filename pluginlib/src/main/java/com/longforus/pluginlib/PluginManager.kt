package com.longforus.pluginlib

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.widget.Toast
import dalvik.system.DexClassLoader

@SuppressLint("StaticFieldLeak")
object PluginManager {
    lateinit var mContext: Context
    var pluginAPK: PluginAPK? = null
    fun init(context: Context) {
        mContext = context.applicationContext
    }

    fun loadPlugin(path: String) {
        val packageInfo = mContext.packageManager.getPackageArchiveInfo(
            path,
            PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES
        )
            ?: return

        val classloader = createClassLoader(path)
        val assetManager = createAssetManager(path)
        val resources = createResources(assetManager)
        pluginAPK = PluginAPK(packageInfo, resources, classloader)
        Toast.makeText(mContext,"load success",Toast.LENGTH_SHORT).show()
    }

    private fun createResources(assetManager: AssetManager): Resources {
        return Resources(assetManager, mContext.resources.displayMetrics, mContext.resources.configuration)
    }

    private fun createAssetManager(path: String): AssetManager {
        val newInstance = AssetManager::class.java.newInstance()
        val method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
        method.invoke(newInstance, path)
        return newInstance
    }

    private fun createClassLoader(path: String): DexClassLoader {
        val file = mContext.getDir("dex", Context.MODE_PRIVATE)
        return DexClassLoader(path, file.absolutePath, null, mContext.classLoader)
    }


}