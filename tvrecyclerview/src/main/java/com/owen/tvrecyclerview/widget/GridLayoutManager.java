package com.owen.tvrecyclerview.widget;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;

public class GridLayoutManager extends androidx.recyclerview.widget.GridLayoutManager {
    public GridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }
}
