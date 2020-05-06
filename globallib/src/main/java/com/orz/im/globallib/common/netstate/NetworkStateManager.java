package com.orz.im.globallib.common.netstate;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.orz.im.globallib.event.UnPeekLiveData;

/**
 * Created by Orz on .
 */
public class NetworkStateManager implements LifecycleObserver {

    private static final NetworkStateManager S_MANAGER = new NetworkStateManager();
    public final UnPeekLiveData<NetState> mNetworkStateCallback = new UnPeekLiveData<>();
    private NetworkStateReceive mNetworkStateReceive;

    private NetworkStateManager() {
    }

    public static NetworkStateManager getInstance() {
        return S_MANAGER;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(@NonNull LifecycleOwner owner) {
        mNetworkStateReceive = new NetworkStateReceive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        if (owner instanceof AppCompatActivity) {
            ((AppCompatActivity) owner).registerReceiver(mNetworkStateReceive, filter);
        } else if (owner instanceof Fragment) {
            ((Fragment) owner).requireActivity()
                    .registerReceiver(mNetworkStateReceive, filter);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(@NonNull LifecycleOwner owner) {
        if (owner instanceof AppCompatActivity) {
            ((AppCompatActivity) owner).unregisterReceiver(mNetworkStateReceive);
        } else if (owner instanceof Fragment) {
            ((Fragment) owner).requireActivity()
                    .unregisterReceiver(mNetworkStateReceive);
        }
    }
}