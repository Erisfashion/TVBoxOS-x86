package com.github.tvbox.osc.base;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import com.github.tvbox.osc.cache.RoomDataManger;
import com.github.tvbox.osc.callback.EmptyCallback;
import com.github.tvbox.osc.callback.LoadingCallback;
import com.github.tvbox.osc.server.ControlManager;
import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.hawk.Hawk;
import java.security.Security;
import org.conscrypt.Conscrypt;

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // Android 4.2.2 (Dalvik) 必须先加载分包，否则后续代码报 NoClassDefFoundError
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        // 关键：在任何网络操作前插入 Conscrypt，使 Android 4.2.2 原生支持现代 TLS 1.2+
        try {
            Security.insertProviderAt(Conscrypt.newProvider(), 1);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        super.onCreate();
        instance = this;

        // 初始化本地轻量存储
        Hawk.init(this).build();

        // 初始化数据库与后台服务控制组件
        RoomDataManger.init(this);
        ControlManager.init(this);

        // 初始化状态页加载器
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .commit();
    }
}
