package com.longforus.pluginlib

import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.Resources
import dalvik.system.DexClassLoader

data class PluginAPK(val packageInfo: PackageInfo, val resources: Resources, val classLoader: DexClassLoader){
    val assetManager:AssetManager = resources.assets
}