package com.orz.im.globallib.common.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;

/**
 * Created by Orz on .
 */
public class NetworkStateReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (!NetworkUtils.isConnected()) {
                    NetworkStateManager.getInstance().mNetworkStateCallback.postValue(new NetState("网络不给力啊",false));
                }
        }
    }
}
