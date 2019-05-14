package com.longforus.pluginlib

import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * 插件activity的代理对象,所有要启动的插件activity都通过它,它将把当前正常生命周期进行转发,
 * 并且代理所有需要代理插件activity所有需要真正activity对象的方法的调用
 */
class ProxyActivity: FragmentActivity(){

    var clientActivity:IPlugin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //要启动的插件activity的类名
        val className = intent.getStringExtra("className")
        //通过可以读取插件包中class的classloader读取目标class
        val loadClass = PluginManager.pluginAPK?.classLoader?.loadClass(className) ?: throw RuntimeException("need load apk first")
        //反射生成实例,这里的实例实际上只是一个普通的class对象,并不具备activity的真正特征
        val instance = loadClass.newInstance()
        if (instance is IPlugin) {
            clientActivity = instance
            //传入宿主
            instance.onAttach(this)
            val bundle = Bundle()
            //传入外部运行的标识
            bundle.putBoolean("isInternal",false)
            //手动触发插件activity的生命周期方法
            instance.onCreate(bundle)
        } else {
            throw RuntimeException("target activity need implement IPlugin")
        }
    }


    override fun onStart() {
        super.onStart()
        //生命周期方法转发
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

    //這样才能读取到插件包内部的资源,class等
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