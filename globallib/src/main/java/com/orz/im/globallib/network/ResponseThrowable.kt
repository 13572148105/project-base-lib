package com.orz.im.globallib.network

/**
 * Created by Orz on .
 */
class ResponseThrowable : Exception {
    var code: Int
    var errMsg: String?

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        code = error.getKey()
        errMsg = error.getValue()
    }

    constructor(code: Int, msg: String?, e: Throwable? = null) : super(e) {
        this.code = code
        this.errMsg = msg
    }
}

