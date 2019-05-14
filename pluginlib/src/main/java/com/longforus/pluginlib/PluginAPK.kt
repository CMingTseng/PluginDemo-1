package com.longforus.pluginlib

import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.Resources
import dalvik.system.DexClassLoader

/**
 * 一个apk插件包能够作为插件运行的基本要求对象
 */
data class PluginAPK(val packageInfo: PackageInfo,
                     //读取插件包中的资源文件
                     val resources: Resources,
                     //读取插件包中的class
                     val classLoader: DexClassLoader){
    val assetManager:AssetManager = resources.assets
}