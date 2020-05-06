package com.orz.im.globallib.network

import com.google.gson.annotations.SerializedName

/**
 * Created by Orz on .
 */
data class HttpResponseBody<T>(
    @SerializedName("code")
    var code: Int = -1,
    @SerializedName("msg")
    var msg: String?,
    var data: T?
){
    fun isSuccessful():Boolean = code == 0
}
