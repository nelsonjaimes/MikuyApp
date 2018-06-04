package com.restaurant.project.mikuyapp.letter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.utils.AnimationUtil;

import java.util.List;

public class LettherAdapter extends RecyclerView.Adapter<LettherAdapter.LetterViewHolder> {

    private final Context context;
    private List<Plate> plateList;

    public LettherAdapter(Context context) {
        this.context = context;
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
        holder.setLetterPlate(plateList.get(position), position);
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
        plateList.get(position).setAcount(1);
        plateList.get(position).setFirstAggregate(false);
        notifyItemChanged(position);
    }

    public List<Plate> getPlateList() {
        return plateList;
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
        private final ImageView ivCheck;
        private final TextView tvName;
        private final TextView tvPrice;
        private final ImageButton btnMore;
        private final ImageButton btnLess;
        private final TextView tvAccountant;
        private final LinearLayout llAccountant;
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
            btnLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        int count = Integer.parseInt(tvAccountant.getText().toString());
                        count++;
                        tvAccountant.setText(String.valueOf(count));
                        plateList.get(position).setAcount(count);
                        tvPrice.setText(context.getString(R.string.formatPrice,
                                plateList.get(position).getAmount()));
                    }
                }
            });

            btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        int count = Integer.parseInt(tvAccountant.getText().toString());
                        if (count > 1) {
                            count--;
                            tvAccountant.setText(String.valueOf(count));
                            plateList.get(position).setAcount(count);
                            tvPrice.setText(context.getString(R.string.formatPrice,
                                    plateList.get(position).getAmount()));
                        }
                    }
                }
            });
        }

        void setLetterPlate(Plate plate, int position) {
            tvName.setText(plate.getName());
            tvPrice.setText(context.getString(R.string.formatPrice, plate.getAmount()));
            tvAccountant.setText(String.valueOf(plate.getAcount()));
            if (plate.isAggregate()) {
                if (plate.isFirstAggregate()) {
                    llAccountant.setVisibility(View.VISIBLE);
                } else {
                    AnimationUtil.slideRightAnimationView(llAccountant, context);
                    plateList.get(position).setFirstAggregate(true);
                }
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

