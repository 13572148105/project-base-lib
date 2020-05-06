package com.orz.im.globallib.common.ext

/**
 * Created by Orz on .
 */
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

/**
 * 跳转浏览器
 */
fun Context.openWithDefault(url: String) {
    try {
        val intent = Intent().apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            action = "android.intent.action.VIEW"
            data = Uri.parse(url)
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
        startActivity(intent)
    } catch (ex: Exception) {
        Log.d("error_open_web",ex.message?:"")
    }
}