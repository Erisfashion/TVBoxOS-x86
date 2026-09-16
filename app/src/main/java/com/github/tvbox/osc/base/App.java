package com.github.tvbox.osc.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.multidex.MultiDex;
import com.github.tvbox.osc.bean.VodInfo;
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
    private Activity currentActivity;
    private VodInfo vodInfo;
    private String dashData;

    public static App getInstance() {
        return instance;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public VodInfo getVodInfo() {
        return vodInfo;
    }

    public void setVodInfo(VodInfo vodInfo) {
        this.vodInfo = vodInfo;
    }

    public String getDashData() {
        return dashData;
    }

    public void setDashData(String dashData) {
        this.dashData = dashData;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        try {
            Security.insertProviderAt(Conscrypt.newProvider(), 1);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        super.onCreate();
        instance = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

            @Override
            public void onActivityStarted(Activity activity) {}

            @Override
            public void onActivityResumed(Activity activity) {
                currentActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {}

            @Override
            public void onActivityStopped(Activity activity) {}

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (currentActivity == activity) {
                    currentActivity = null;
                }
            }
        });

        Hawk.init(this).build();

        // Room 数据库在调用各 Dao 时会自动单例构建，去掉不存在的 init 调用
        // RoomDataManger.init(this);
        ControlManager.init(this);

        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .commit();
    }
}
