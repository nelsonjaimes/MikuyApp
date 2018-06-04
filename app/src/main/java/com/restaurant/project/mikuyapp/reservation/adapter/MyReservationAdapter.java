package com.restaurant.project.mikuyapp.reservation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MyReservationEntity;

import java.util.List;

public class MyReservationAdapter extends RecyclerView.Adapter<MyReservationAdapter.
        ReservationViewHolder> {
    private List<MyReservationEntity> entityList;
    private final Context context;

    public MyReservationAdapter(Context context) {
        this.context = context;
    }

    public void setEntityList(List<MyReservationEntity> entityList) {
        this.entityList = entityList;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.row_my_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        holder.setMyReservation(entityList.get(position));
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCodeReserse;
        private final TextView tvDateReservation;

        ReservationViewHolder(View itemView) {
            super(itemView);
            tvCodeReserse = itemView.findViewById(R.id.tvCodeReserse);
            tvDateReservation = itemView.findViewById(R.id.tvDateReservation);
        }

        void setMyReservation(MyReservationEntity entity) {
            tvCodeReserse.setText(entity.getCodeReserve());
            tvDateReservation.setText(entity.getDatehour());
            if (entity.getState() == 0) {
                tvCodeReserse.setBackgroundResource(R.drawable.circle_expire);
            } else {
                tvCodeReserse.setBackgroundResource(R.drawable.circle_reserve);
            }
        }
    }
}
