package com.longforus.pluginlib

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

/**
 * 所有的插件内的activity都要继承于此类,以实现生命周期,貌似超类不能使用AppCompatActivity,否则会报AppCompatDelegate找不到的错误.
 * 不知道其他插件框架是怎么处理的
 */
@SuppressLint("MissingSuperCall")
abstract class PluginActivity : FragmentActivity(), IPlugin {
    val TAG = "PluginActivity"
    /**
     * 标识是作为插件状态运行还是直接运行,如果是直接运行,不作特殊处理
     */
    var isInternal = true
    /**
     *宿主activity所有activity自带的方法调用都需要通过它来调用.因为作为插件中的activity,运行时相当于一个普通的类,
     * 不具备activity的'上下文'环境,不仅仅是context哦.
     */
    lateinit var mProxyActivity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        isInternal = savedInstanceState?.getBoolean("isInternal", true) ?: true
        if (isInternal) {
            super.onCreate(savedInstanceState)
            mProxyActivity = this
        }
        Log.d(TAG, "onCreate $isInternal")
    }


    override fun setContentView(layoutResID: Int) {
        if (isInternal) {
            super.setContentView(layoutResID)
        } else {
            //实际上view都设置给代理activity了,我们看到了这个插件activity的view内容,感觉这个activity启动了,其实真正启动的
            //是代理activity,只是把插件activity的内容,行为都传给了代理activity
            mProxyActivity.setContentView(layoutResID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (isInternal) {
            super.onActivityResult(requestCode, resultCode, data)
        }
        Log.d(TAG, "onActivityResult $isInternal")
    }

    /**
     * 这里的生命周期是mProxyActivity代理调用进来的,所以在插件运行状态不在调用mProxyActivity的生命周期方法
     */
    override fun onStart() {
        if (isInternal) {
            super.onStart()
        }
        Log.d(TAG, "onStart $isInternal")
    }


    override fun onRestart() {
        if (isInternal) {
            super.onRestart()
        }
        Log.d(TAG, "onRestart $isInternal")
    }

    override fun onResume() {
        if (isInternal) {
            super.onResume()
        }
        Log.d(TAG, "onResume $isInternal")
    }

    override fun onPause() {
        if (isInternal) {
            super.onPause()
        }
        Log.d(TAG, "onPause $isInternal")
    }

    override fun onStop() {
        if (isInternal) {
            super.onStop()
        }
        Log.d(TAG, "onStop $isInternal")
    }

    override fun onDestroy() {
        if (isInternal) {
            super.onDestroy()
        }
        Log.d(TAG, "onDestroy $isInternal")
    }

    override fun onAttach(proxyActivity: Activity) {
        mProxyActivity = proxyActivity
    }

}