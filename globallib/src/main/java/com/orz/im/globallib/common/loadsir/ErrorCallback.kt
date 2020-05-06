package com.orz.im.globallib.common.loadsir

import com.kingja.loadsir.callback.Callback
import com.orz.im.globallib.R


/**
 * @author: orz on 2018/10/18
 */
class ErrorCallback: Callback() {
    override fun onCreateView(): Int {
        return R.layout.common_error_view
    }
}