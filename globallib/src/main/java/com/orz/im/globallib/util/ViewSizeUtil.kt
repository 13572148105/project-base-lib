package com.orz.im.globallib.util

import android.view.View
import android.view.ViewTreeObserver


/**
 * @author: orz on 2018/10/27
 * 测量View 宽高工具类
 */
object ViewSizeUtil {
    fun getViewMeasureSize(view:View): ViewMeasureBo {
        val viewMeasureBo: ViewMeasureBo by lazy { ViewMeasureBo() }
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                viewMeasureBo.measureWidth = view.measuredWidth
                viewMeasureBo.measureHeight = view.measuredHeight
            }
        })
        return viewMeasureBo
    }
}
data class ViewMeasureBo(var measureWidth: Int = 0, var measureHeight: Int = 0)