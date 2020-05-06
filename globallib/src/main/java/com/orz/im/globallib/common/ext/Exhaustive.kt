package com.orz.im.globallib.common.ext

/**
 * Created by Orz on .
 * 如果想要在使用when语句时获取相同的编译器提示，可以添加如下扩展属性
 */
val <T> T.exhaustive:T get() = this