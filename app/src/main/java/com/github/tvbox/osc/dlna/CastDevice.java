package com.github.tvbox.osc.dlna;

public class CastDevice {
    public static final int TYPE_TVBOX = 1;
    public static final int TYPE_DLNA = 2;

    private int type;
    private String id = "";
    private String name = "";

    public CastDevice() {
    }

    public static CastDevice tvbox(String host) {
        CastDevice device = new CastDevice();
        device.type = TYPE_TVBOX;
        device.id = host;
        return device;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id != null ? id : "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }
}
