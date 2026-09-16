package com.github.tvbox.osc.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        if (isUseEventBus()) {
            try {
                EventBus.getDefault().register(this);
            } catch (Throwable ignored) {}
        }
        init();
    }

    protected abstract int getLayoutResID();

    // 补齐基础 init 虚方法，供子类重写并自动在 onCreate 中被调用
    protected void init() {
    }

    // 补齐事件总线开关，供 History/Home/SettingActivity 重写开启
    protected boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isUseEventBus()) {
            try {
                EventBus.getDefault().unregister(this);
            } catch (Throwable ignored) {}
        }
    }
}
