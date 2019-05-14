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
        //获取插件包中的activity和service信息,官方竟然有这种api
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

    /**
     * 根据当前主APP的配置生成插件包的resource
     */
    private fun createResources(assetManager: AssetManager): Resources {
        return Resources(assetManager, mContext.resources.displayMetrics, mContext.resources.configuration)
    }

    /**
     * 反射生成资产管理器
     */
    private fun createAssetManager(path: String): AssetManager {
        val newInstance = AssetManager::class.java.newInstance()
        val method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
        method.invoke(newInstance, path)
        return newInstance
    }

    private fun createClassLoader(path: String): DexClassLoader {
        val file = mContext.getDir("dex", Context.MODE_PRIVATE)
        //path 要读取的dex的路径,竟然支持直接使用apk的路径
        //file.absolutePath 看样子好像是dex的优化解压路径
        //以当前主app的classloader为parent.继承很多属性吧
        return DexClassLoader(path, file.absolutePath, null, mContext.classLoader)
    }


}