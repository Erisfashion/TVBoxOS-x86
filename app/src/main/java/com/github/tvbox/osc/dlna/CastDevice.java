package com.github.tvbox.osc.dlna;

public class CastDevice {
    private String name;
    private String ip;

    public CastDevice(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public static CastDevice dlna(Object device) {
        return null;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }
}
