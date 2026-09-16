package com.owen.tvrecyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TvRecyclerView extends RecyclerView {

    // 补充暴露的间距字段，供 UserFragment 等页面直接读取
    public int mHorizontalSpacingWithMargins = 0;
    public int mVerticalSpacingWithMargins = 0;
    
    public TvRecyclerView(Context context) { 
        super(context); 
    }
    public TvRecyclerView(Context context, @Nullable AttributeSet attrs) { 
        super(context, attrs); 
    }
    public TvRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    }

    // 1. 补全回调接口定义
    public interface OnItemListener {
        void onItemPreSelected(TvRecyclerView parent, View itemView, int position);
        void onItemSelected(TvRecyclerView parent, View itemView, int position);
        void onItemClick(TvRecyclerView parent, View itemView, int position);
    }

    public interface OnInBorderKeyEventListener {
        boolean onInBorderKeyEvent(int direction, View focused);
    }

    // 2. 补全注册监听方法
    public void setOnItemListener(OnItemListener listener) {}
    public void setOnInBorderKeyEventListener(OnInBorderKeyEventListener listener) {}

    // 3. 补全焦点及选中/定位控制方法
    public void setSelection(int position) {
        scrollToPosition(position);
    }

    public void setSelectedPosition(int position) {
        scrollToPosition(position);
    }

    public void setSelectionWithSmooth(int position) {
        smoothScrollToPosition(position);
    }

    // 4. 补全可见位置获取方法
    public int getFirstVisiblePosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return 0;
    }

    public int getLastVisiblePosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return 0;
    }

    // 5. 补全间距与状态滚动判断方法
    public void setSpacingWithMargins(int verticalSpacing, int horizontalSpacing) {
        this.mVerticalSpacingWithMargins = verticalSpacing;
        this.mHorizontalSpacingWithMargins = horizontalSpacing;
    }

    public boolean isScrolling() {
        return getScrollState() != SCROLL_STATE_IDLE;
    }
}
