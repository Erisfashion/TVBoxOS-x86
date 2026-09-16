package com.github.tvbox.osc.ui.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    private OnCastListener mCastListener;

    public interface OnCastListener {
        void onCast(CastDevice device);
        void onCancel();
    }

    public void setOnCastListener(OnCastListener listener) {
        this.mCastListener = listener;
    }

    public CastDeviceDialog(@NonNull Context context, CastVideo video) {
        super(context);
        this.video = video;
    }

    @Override
    protected void init() {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CastDeviceAdapter(mDevices);
        mRecyclerView.setAdapter(mAdapter);
        setContentView(mRecyclerView);

        mAdapter.setOnItemClickListener(position -> {
            if (position >= 0 && position < mDevices.size()) {
                CastDevice device = mDevices.get(position);
                if (mCastListener != null) {
                    mCastListener.onCast(device);
                }
                castVideo(device);
                dismiss();
            }
        });

        searchDevices();
    }

    private void searchDevices() {
        DLNACastManager.get().setDeviceListener(new DLNACastManager.DeviceListener() {
            @Override
            public void onDevicesChanged() {
                mDevices.clear();
                if (DLNACastManager.get().getDevices() != null) {
                    mDevices.addAll(DLNACastManager.get().getDevices());
                }
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
        if (mCastListener != null) {
            mCastListener.onCancel();
        }
        super.dismiss();
    }
}
