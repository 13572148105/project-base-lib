package com.orz.im.globallib.common.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.orz.im.globallib.R


/**
 * @author: orz on 2018/10/18
 */
class LoadingCallback: Callback() {
    override fun onCreateView(): Int {
        return R.layout.common_loading_view
    }
    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}