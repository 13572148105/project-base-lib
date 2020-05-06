package com.orz.im.globallib.common.transformer
import com.orz.im.globallib.base.IBaseResponse
import com.orz.im.globallib.network.ResponseThrowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
/**
 * Created by Orz on .
 * 网络请求流式调用转换类
 */

@ExperimentalCoroutinesApi
fun <T> Flow<IBaseResponse<T>>.applyTransform(): Flow<T> {
    return this
        .flowOn(Dispatchers.IO)
        .map {
            if (it.isSuccess()) return@map it.data()
            else throw ResponseThrowable(it.code(), it.msg())
        }
}
