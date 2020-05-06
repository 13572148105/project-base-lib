package com.orz.im.globallib.event

/**
 *   @auther : orz
 *   time   : 2020/04/21
 */
class Message @JvmOverloads constructor(
    var code: Int = 0,
    var msg: String = "",
    var obj: Any? = null
)