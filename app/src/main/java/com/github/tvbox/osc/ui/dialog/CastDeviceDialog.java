package com.github.tvbox.osc.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.tvbox.osc.R;
import com.github.tvbox.osc.dlna.CastDevice;
import com.github.tvbox.osc.dlna.CastVideo;
import com.github.tvbox.osc.dlna.DLNACastManager;
import com.github.tvbox.osc.ui.adapter.CastDeviceAdapter;
import java.util.ArrayList;
import java.util.List;

public class CastDeviceDialog extends BaseDialog {
    private RecyclerView mRecyclerView;
    private CastDeviceAdapter mAdapter;
    private List<CastDevice> mDevices = new ArrayList<>();
    private CastVideo video;

    public CastDeviceDialog(@NonNull Context context, CastVideo video) {
        super(context);
        this.video = video;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.dialog_cast;
    }

    @Override
    protected void init() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CastDeviceAdapter(mDevices);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CastDevice device = mDevices.get(position);
            castVideo(device);
            dismiss();
        });

        searchDevices();
    }

    private void searchDevices() {
        DLNACastManager.get().setDeviceListener(new DLNACastManager.DeviceListener() {
            @Override
            public void onDeviceAdded(CastDevice device) {
                if (!mDevices.contains(device)) {
                    mDevices.add(device);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onDeviceRemoved(CastDevice device) {
                mDevices.remove(device);
                mAdapter.notifyDataSetChanged();
            }
        });
        DLNACastManager.get().search();
    }

    private void castVideo(CastDevice device) {
        if (video == null) return;
        DLNACastManager.get().cast(device, video, new DLNACastManager.CastCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    @Override
    public void dismiss() {
        DLNACastManager.get().stopSearch();
        super.dismiss();
    }
}
