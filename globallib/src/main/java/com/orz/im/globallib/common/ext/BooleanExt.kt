package com.orz.im.globallib.common.ext

/**
 * Created by Orz on .
 * 扩展Boolean 语意===>让链式调用更加顺滑
 */
sealed class BooleanExt<out T>
    class TransferData<T>(val data:T):BooleanExt<T>()
    object Otherwise:BooleanExt<Nothing>()

    inline fun <T> Boolean.yes(block:() -> T):BooleanExt<T> =
        when{
            this -> TransferData(block.invoke())
            else -> Otherwise
        }
    inline fun <T> BooleanExt<T>.otherwise(block: () -> T) : T =
        when(this){
            is Otherwise -> block()
            is TransferData -> data
        }
