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
        default void onCast(CastDevice device) {}
        default void onCancel() {}
        default void onDismiss() {}
        default void onItemClick(CastDevice device) {}
        default void onSelected(CastDevice device) {}
        default void onDeviceAdded(CastDevice device) {}
        default void onDeviceRemoved(CastDevice device) {}
        default void onDevicesChanged() {}
    }

    public void setOnCastListener(OnCastListener listener) {
        this.mCastListener = listener;
    }

    public CastDeviceDialog(@NonNull Context context, CastVideo video) {
        super(context);
        this.video = video;
        initView();
    }

    public CastDeviceDialog(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        // 使用纯代码创建 RecyclerView，避免依赖特定的 XML 布局 ID 导致找不到 ID 报错
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
                    mCastListener.onItemClick(device);
                    mCastListener.onSelected(device);
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
            public void onDeviceAdded(CastDevice device) {
                if (!mDevices.contains(device)) {
                    mDevices.add(device);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
                if (mCastListener != null) {
                    mCastListener.onDeviceAdded(device);
                    mCastListener.onDevicesChanged();
                }
            }


            public void onDeviceRemoved(CastDevice device) {
                mDevices.remove(device);
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                if (mCastListener != null) {
                    mCastListener.onDeviceRemoved(device);
                    mCastListener.onDevicesChanged();
                }
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
            mCastListener.onDismiss();
        }
        super.dismiss();
    }
}
