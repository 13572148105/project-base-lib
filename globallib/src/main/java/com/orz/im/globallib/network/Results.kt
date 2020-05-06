package com.orz.im.globallib.network

/**
 * Created by Orz on .
 */
sealed class Results<out T>{

    companion object {
        fun <T> success(result: T?): Results<T> = Success(result)
        fun <T> failure(error: ResponseThrowable): Results<T> = Failure(error)
    }

    data class Failure(val error: ResponseThrowable) : Results<Nothing>()
    data class Success<out T>(val data: T?) : Results<T>()
}