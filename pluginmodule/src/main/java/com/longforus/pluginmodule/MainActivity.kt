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
            //在插件activity内,所有需要使用context或者说this的地方都需要使用代理activity
            val intent = Intent(mProxyActivity, ProxyActivity::class.java)
            intent.putExtra("className","com.longforus.pluginmodule.Main2Activity")
            //所有activity内调用的方法也都要通过代理activity调用,因为运行时,插件activity不是一个真正的activity,只是一个普通的对象
            //但是如果這样的话,不是public的方法不是就不能调用到了么? 我现在想到的方法是在代理activity中创建代理方法,插件activity调用代理方法
            //代理方法再调用真正的目标方法
            //如果不想通过代理activity调用的话,PluginActivity就要尽可能的复写方法,在内部做判断,将相应的方法转发给代理activity.
            mProxyActivity.startActivity(intent)
        }
    }



}
