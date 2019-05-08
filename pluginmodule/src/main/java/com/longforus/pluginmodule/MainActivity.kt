package com.longforus.pluginmodule

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.longforus.pluginlib.PluginActivity
import com.longforus.pluginlib.ProxyActivity

class MainActivity : PluginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mProxyActivity.findViewById<View>(R.id.tv).setOnClickListener {
            val intent = Intent(mProxyActivity, ProxyActivity::class.java)
            intent.putExtra("className","com.longforus.pluginmodule.Main2Activity")
            mProxyActivity.startActivity(intent)
        }
    }



}
