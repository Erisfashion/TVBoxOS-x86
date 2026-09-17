package com.github.tvbox.osc.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import com.github.tvbox.osc.bean.VodInfo;
import com.github.tvbox.osc.callback.EmptyCallback;
import com.github.tvbox.osc.callback.LoadingCallback;
import com.kingja.loadsir.core.LoadSir;
import com.lzy.okgo.OkGo;
import com.orhanobut.hawk.Hawk;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class App extends Application {

    private static App instance;
    private String dashData;
    private Activity currentActivity;
    private VodInfo vodInfo;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // 1. 最先注册崩溃日志拦截器（纯原生代码，保证自身不崩溃）
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                writeCrashLogToFile(ex);
                if (defaultHandler != null) {
                    defaultHandler.uncaughtException(thread, ex);
                }
            }
        });

        // 2. 初始化分包支持
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        try {
            Hawk.init(this).build();
            OkGo.getInstance().init(this);
            LoadSir.beginBuilder()
                    .addCallback(new EmptyCallback())
                    .addCallback(new LoadingCallback())
                    .setDefaultCallback(LoadingCallback.class)
                    .commit();
        } catch (Throwable t) {
            writeCrashLogToFile(t);
        }

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
    }

    private static void writeCrashLogToFile(Throwable ex) {
        try {
            File logFile = new File(Environment.getExternalStorageDirectory(), "tvbox_crash.log");
            PrintWriter pw = new PrintWriter(new FileWriter(logFile, false));
            pw.println("=== TVBox Crash Info ===");
            pw.println("Date: " + new java.util.Date());
            pw.println("Android: " + android.os.Build.VERSION.RELEASE + " (API " + android.os.Build.VERSION.SDK_INT + ")");
            pw.println("Device: " + android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL);
            pw.println("CPU_ABI: " + android.os.Build.CPU_ABI + " / " + android.os.Build.CPU_ABI2);
            pw.println("-------------------------");
            ex.printStackTrace(pw);
            
            Throwable cause = ex.getCause();
            while (cause != null) {
                pw.println("Caused by:");
                cause.printStackTrace(pw);
                cause = cause.getCause();
            }
            pw.flush();
            pw.close();
        } catch (Throwable ignored) {}
    }

    public static App getInstance() {
        return instance;
    }

    public String getDashData() {
        return dashData;
    }

    public void setDashData(String dashData) {
        this.dashData = dashData;
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
}
