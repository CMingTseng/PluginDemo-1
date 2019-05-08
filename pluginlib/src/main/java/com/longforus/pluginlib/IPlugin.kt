package com.longforus.pluginlib

import android.app.Activity
import android.content.Intent
import android.os.Bundle

interface IPlugin{
    fun onAttach(proxyActivity:Activity)

    fun onCreate(savedInstanceState: Bundle?)


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

     fun onStart()

     fun onRestart()

     fun onResume()

     fun onPause()

     fun onStop()

     fun onDestroy()
}