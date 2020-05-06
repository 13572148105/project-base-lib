package com.orz.im.globallib.common.ext

/**
 * Created by Orz on .
 */
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.orz.im.globallib.network.ResponseThrowable
import com.orz.im.globallib.network.Results

fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>?, observer: (t: T) -> Unit) {
    liveData?.observe(this, Observer { it?.let { t -> observer(t) } })
}

/**
 * 网络响应结果处理函数
 */
fun <T> handlerResult(
    response: Results<T>,
    success: (T?) -> Unit,
    error: (ResponseThrowable) -> Unit) {
    when(response){
        is Results.Success ->{
            success(response.data)
        }
        is Results.Failure ->{
            error(response.error)
        }
    }.exhaustive
}

fun <T> handlerResult(
    response: Results<T>,
    success: (T?) -> Unit) {
    when(response){
        is Results.Success ->{
            success(response.data)
        }
        else->{}
    }
}