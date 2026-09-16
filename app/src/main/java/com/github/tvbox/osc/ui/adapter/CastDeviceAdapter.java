package com.github.tvbox.osc.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.tvbox.osc.dlna.CastDevice;
import java.util.List;

public class CastDeviceAdapter extends RecyclerView.Adapter<CastDeviceAdapter.ViewHolder> {
    private List<CastDevice> mDevices;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CastDeviceAdapter(List<CastDevice> devices) {
        this.mDevices = devices;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = new TextView(parent.getContext());
        view.setPadding(40, 30, 40, 30);
        view.setTextSize(16);
        view.setTextColor(0xFFFFFFFF);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CastDevice device = mDevices.get(position);
        holder.textView.setText(device != null ? device.toString() : "");
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDevices != null ? mDevices.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView;
        }
    }
}
