package com.orz.im.globallib.common.netstate;

/**
 * Created by Orz on .
 */
public class NetState {
    private String responseCode;
    private boolean success = true;

    public NetState(String responseCode, boolean success) {
        this.responseCode = responseCode;
        this.success = success;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
