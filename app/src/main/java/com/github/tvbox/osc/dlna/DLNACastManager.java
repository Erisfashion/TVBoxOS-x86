package com.github.tvbox.osc.dlna;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DLNACastManager {
    private static final DLNACastManager instance = new DLNACastManager();

    public static DLNACastManager get() {
        return instance;
    }

    public void init(Context context) {}

    public void release(Context context) {}

    public void search() {}

    public void setDeviceListener(DeviceListener listener) {}

    public List<CastDevice> getDevices() {
        return new ArrayList<>();
    }

    public void cast(CastDevice device, CastVideo video, CastCallback callback) {}

    public interface DeviceListener {
        void onDevicesChanged();
        void onDeviceAdded(CastDevice device);
    }

    public interface CastCallback {
        void onSuccess();
        void onError(String msg);
    }
}
