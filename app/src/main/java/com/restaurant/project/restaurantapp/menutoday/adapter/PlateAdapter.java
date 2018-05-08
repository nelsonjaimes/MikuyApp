package com.restaurant.project.restaurantapp.menutoday.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.project.restaurantapp.R;
import com.restaurant.project.restaurantapp.menutoday.model.Plate;
import com.restaurant.project.restaurantapp.menutoday.ui.PlateRecyclerListener;
import com.restaurant.project.restaurantapp.utils.AnimationUtil;

import java.util.List;

public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.PlateTodayViewHolder> {

    public static final String TYPE_MENU_ENTRY = "menuEntry";
    public static final String TYPE_MENU_DESSERT = "menuDessert";
    public static final String TYPE_MENU_BACKGROUND = "menuBackground";
    public static final String TYPE_LETTER_FOOD = "letterFood";
    private Context context;
    private List<Plate> plateList;
    private PlateRecyclerListener plateRecyclerListener;

    public PlateAdapter(Context context) {
        this.context = context;
    }

    public void setPlateList(List<Plate> plateList) {
        this.plateList = plateList;
    }

    public void setPlateRecyclerListener(PlateRecyclerListener plateRecyclerListener) {
        this.plateRecyclerListener = plateRecyclerListener;
    }

    @Override
    public void onBindViewHolder(@NonNull PlateTodayViewHolder holder, int position) {
        holder.setTodayPlate(plateList.get(position));
    }

    @NonNull
    @Override
    public PlateTodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_menutoday_plate, parent,
                false);
        return new PlateTodayViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return plateList.size();
    }

    private void setSelectItem(int position) {
        for (int i = 0; i < plateList.size(); i++) {
            plateList.get(i).setAggregate(false);
        }
        plateList.get(position).setAggregate(true);
        notifyDataSetChanged();
    }

    class PlateTodayViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView tvName;
        private ImageView ivCheck;
        private TextView tvPrice;
        private int position;

        PlateTodayViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivCheck = itemView.findViewById(R.id.ivCheck);
            itemView.setOnClickListener(this);
        }

        void setTodayPlate(Plate plate) {
            tvName.setText(plate.getName());
            tvPrice.setText(context.getString(R.string.formatPrice, plate.getPrice()));
            if (plate.getCategory().equals(TYPE_MENU_ENTRY) ||
                    plate.getCategory().equals(TYPE_MENU_DESSERT)) {
                tvPrice.setVisibility(View.INVISIBLE);
            } else {
                tvPrice.setVisibility(View.VISIBLE);
            }
            if (plate.isAggregate()) {
                AnimationUtil.showAnimationView(ivCheck, context);
            } else {
                ivCheck.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                setSelectItem(position);
                plateRecyclerListener.selectItem(plateList.get(position), position);
            }
        }
    }
}
