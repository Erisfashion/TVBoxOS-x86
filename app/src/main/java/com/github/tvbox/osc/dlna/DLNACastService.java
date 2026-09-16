package com.github.tvbox.osc.dlna;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DLNACastService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
