package com.restaurant.project.mikuyapp.scan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.utils.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

public class ScannerAdapter extends RecyclerView.Adapter<ScannerAdapter.ScanViewHolder> {

    public interface Callback {
        void onClickHost(String ipAddress);
    }

    private final Context context;
    private final List<Host> hostList;
    private ScannerAdapter.Callback callback;

    public ScannerAdapter(Context context) {
        this.context = context;
        hostList = new ArrayList<>();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_host, parent, false);
        return new ScanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScanViewHolder holder, int position) {
        holder.setHost(hostList.get(position));
    }

    @Override
    public int getItemCount() {
        return hostList.size();
    }

    public void clear() {
        this.hostList.clear();
    }

    public void addHost(Host host) {
        this.hostList.add(host);
        notifyDataSetChanged();
    }

    public void addList(List<Host> hostList) {
        this.hostList.clear();
        this.hostList.addAll(hostList);
        notifyDataSetChanged();
    }

    private void setChangeItem(int position) {
        for (int i = 0; i < hostList.size(); i++) {
            hostList.get(i).setSelect(false);
        }
        hostList.get(position).setSelect(true);
        notifyDataSetChanged();
    }

    class ScanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvSSID;
        private final TextView tvIP;
        private final ImageView ivCheck;
        private final TextView tvMac;
        int position = RecyclerView.NO_POSITION;

        ScanViewHolder(View itemView) {
            super(itemView);
            tvSSID = itemView.findViewById(R.id.tvSSID);
            ivCheck = itemView.findViewById(R.id.ivCheck);
            tvIP = itemView.findViewById(R.id.tvIP);
            tvMac = itemView.findViewById(R.id.tvMac);
            itemView.setOnClickListener(this);
        }

        void setHost(Host host) {
            tvSSID.setText(host.getName());
            tvIP.setText(host.getIpAddress());
            tvMac.setText(host.getHardwareAddress());
            if (host.isSelect()) AnimationUtil.showAnimationView(ivCheck, context);
            else ivCheck.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && callback != null) {
                setChangeItem(position);
                callback.onClickHost(hostList.get(position).getIpAddress());
            }
        }
    }
}
