package com.github.tvbox.osc.dlna;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

public class DLNACastManager implements ServiceConnection {
    private static final DLNACastManager instance = new DLNACastManager();

    public static DLNACastManager getInstance() {
        return instance;
    }

    public void init(Context context) {
    }

    public void start() {
    }

    public void stop() {
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }
}
