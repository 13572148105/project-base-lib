package com.orz.im.globallib.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.hjq.bar.TitleBar
import com.kingja.loadsir.core.LoadSir
import com.orz.im.globallib.common.SmartRefreshHelper
import com.orz.im.globallib.common.loadsir.EmptyCallback
import com.orz.im.globallib.common.loadsir.ErrorCallback
import com.orz.im.globallib.common.loadsir.LoadingCallback
import com.orz.im.globallib.widget.title.TitleBarDefaultStyle
import com.tencent.mmkv.MMKV


/**
 * Created by Orz on .
 */
open class BaseApplication:Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        initLoadSir()
        initTitleBar()
        SmartRefreshHelper.setDefaultConfig()

        val rootDir = MMKV.initialize(this)
        println("mmkv root: $rootDir")
    }

    private fun initLoadSir(){
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .addCallback(ErrorCallback())
            .setDefaultCallback(ErrorCallback::class.java)
            .commit()
    }

    private fun initTitleBar(){
        TitleBar.initStyle(TitleBarDefaultStyle(this))
    }
}