package com.longforus.plugindemo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.longforus.pluginlib.PluginManager
import com.longforus.pluginlib.ProxyActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),123)
        }


        btn_load.setOnClickListener {
            PluginManager.init(this)
            val absolutePath = Environment.getExternalStorageDirectory().absolutePath
            println(absolutePath)
            PluginManager.loadPlugin(absolutePath +"/aa.apk")
        }

        btn_go.setOnClickListener {
            //所有插件activity的启动都要通过代理activity启动
            val intent = Intent(this, ProxyActivity::class.java)
            intent.putExtra("className","com.longforus.pluginmodule.MainActivity")
            startActivity(intent)
        }
    }
}
