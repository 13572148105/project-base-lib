package com.orz.im.globallib.util

import android.view.View
import android.widget.Checkable

/**
 *Created by Orz on 2020/1/8.
 *Describe:防止view重复点击utils
 */
var<T: View> T.lastClickTime:Long
set(value) = setTag(1766613352,value)
get() = getTag(1766613352) as?Long?:0
//重复点击事件绑定
inline fun<T:View> T.singleClick(time:Long = 1000L,crossinline block:(T) ->Unit){
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable){
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}