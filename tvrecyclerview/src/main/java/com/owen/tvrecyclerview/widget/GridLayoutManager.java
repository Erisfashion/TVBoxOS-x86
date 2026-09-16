package com.owen.tvrecyclerview.widget;

import android.content.Context;

public class GridLayoutManager extends androidx.recyclerview.widget.GridLayoutManager {
    public GridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public GridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
}
