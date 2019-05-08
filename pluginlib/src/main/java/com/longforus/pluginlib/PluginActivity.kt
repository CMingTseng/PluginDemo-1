package com.longforus.pluginlib

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

@SuppressLint("MissingSuperCall")
abstract class PluginActivity : FragmentActivity(), IPlugin {
    val TAG = "PluginActivity"
    var isInternal = true
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
            mProxyActivity.setContentView(layoutResID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (isInternal) {
            super.onActivityResult(requestCode, resultCode, data)
        }
        Log.d(TAG, "onActivityResult $isInternal")
    }

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