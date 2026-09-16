package com.github.tvbox.osc.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.github.tvbox.osc.callback.EmptyCallback;
import com.github.tvbox.osc.callback.LoadingCallback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;
    protected LoadService mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        int layoutResId = getLayoutResId();
        if (layoutResId != 0) {
            setContentView(layoutResId);
        }
        hideSysBar();
        init();
    }

    protected abstract int getLayoutResId();

    protected abstract void init();

    /**
     * 屏幕自适应基准：根据宽度进行自适应布局
     */
    public boolean isBaseOnWidth() {
        return true;
    }

    /**
     * 绑定 LoadSir 状态页加载服务
     */
    public void setLoadSir(View targetView) {
        if (targetView != null) {
            mLoadService = LoadSir.getDefault().register(targetView);
        }
    }

    public void showSuccess() {
        if (mLoadService != null) {
            mLoadService.showCallback(SuccessCallback.class);
        }
    }

    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    public void showEmpty() {
        if (mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    /**
     * Activity 路由跳转辅助方法
     */
    public void jumpActivity(Class<? extends Activity> targetClass) {
        jumpActivity(targetClass, null);
    }

    public void jumpActivity(Class<? extends Activity> targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSysBar();
        }
    }

    /**
     * 全屏控制：兼容 Android 4.2.2 隐藏状态栏与导航栏，并在 Android 4.4+ 上应用沉浸式粘性标志
     */
    protected void hideSysBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
    }
}
