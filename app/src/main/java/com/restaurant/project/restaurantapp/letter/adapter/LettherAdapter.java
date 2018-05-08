package com.restaurant.project.restaurantapp.letter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restaurant.project.restaurantapp.R;
import com.restaurant.project.restaurantapp.menutoday.model.Plate;
import com.restaurant.project.restaurantapp.menutoday.ui.PlateRecyclerListener;
import com.restaurant.project.restaurantapp.utils.AnimationUtil;

import java.util.List;

public class LettherAdapter extends RecyclerView.Adapter<LettherAdapter.LetterViewHolder> {

    private Context context;
    private PlateRecyclerListener recyclerListener;
    private List<Plate> plateList;

    public LettherAdapter(Context context) {
        this.context = context;
    }

    public void setRecyclerListener(PlateRecyclerListener recyclerListener) {
        this.recyclerListener = recyclerListener;
    }

    public void setPlateList(List<Plate> plateList) {
        this.plateList = plateList;
    }

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_letter_plate, parent,
                false);
        return new LetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterViewHolder holder, int position) {
        holder.setLetterPlate(plateList.get(position));
    }

    @Override
    public int getItemCount() {
        return plateList.size();
    }

    private void setItemSelect(int position) {
        plateList.get(position).setAggregate(true);
        notifyItemChanged(position);
    }

    private void unItemSelect(int position) {
        plateList.get(position).setAggregate(false);
        notifyItemChanged(position);
    }

    private void onClickItem(int position) {
        Plate plate = plateList.get(position);
        if (plate.isAggregate()) {
            unItemSelect(position);
        } else {
            setItemSelect(position);
        }
    }

    class LetterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivCheck;
        private TextView tvName;
        private TextView tvPrice;
        private Button btnMore;
        private Button btnLess;
        private TextView tvAccountant;
        private LinearLayout llAccountant;
        private int position;

        LetterViewHolder(View itemView) {
            super(itemView);
            ivCheck = itemView.findViewById(R.id.ivCheck);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnMore = itemView.findViewById(R.id.btnMore);
            btnLess = itemView.findViewById(R.id.btnLess);
            tvAccountant = itemView.findViewById(R.id.tvAccountant);
            llAccountant = itemView.findViewById(R.id.llAccountant);
            itemView.setOnClickListener(this);
        }

        void setLetterPlate(Plate plate) {
            tvName.setText(plate.getName());
            tvPrice.setText(context.getString(R.string.formatPrice, plate.getPrice()));
            tvAccountant.setText(String.valueOf(plate.getAccountant()));
            if (plate.isAggregate()) {
                AnimationUtil.slideRightAnimationView(llAccountant, context);
                AnimationUtil.showAnimationView(ivCheck, context);
            } else {
                ivCheck.setVisibility(View.INVISIBLE);
                llAccountant.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onClickItem(position);
            }
        }
    }
}

