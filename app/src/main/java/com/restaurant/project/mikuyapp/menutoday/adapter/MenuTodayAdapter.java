package com.restaurant.project.mikuyapp.menutoday.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.menutoday.model.Category;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;
import com.restaurant.project.mikuyapp.menutoday.ui.PlateRecyclerListener;
import com.restaurant.project.mikuyapp.utils.AnimationUtil;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import java.util.List;

import static com.restaurant.project.mikuyapp.utils.Constant.TYPE_MENU_DESSERT;
import static com.restaurant.project.mikuyapp.utils.Constant.TYPE_MENU_ENTRY;

public class MenuTodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_CATEGORY = 2;
    private final Context context;
    private List<Object> objectList;
    private PlateRecyclerListener plateRecyclerListener;

    public MenuTodayAdapter(Context context) {
        this.context = context;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    public void setPlateRecyclerListener(PlateRecyclerListener plateRecyclerListener) {
        this.plateRecyclerListener = plateRecyclerListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PlateTodayViewHolder) {
            Plate plate = (Plate) objectList.get(position);
            ((PlateTodayViewHolder) holder).setTodayPlate(plate);
        } else {
            Category category = (Category) objectList.get(position);
            ((CategoryViewHolder) holder).setCategory(category);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_menutoday_plate,
                parent, false);
        if (viewType == ITEM_CATEGORY) {
            view = LayoutInflater.from(context).inflate(R.layout.row_category,
                    parent, false);
            return new CategoryViewHolder(view);
        }
        return new PlateTodayViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    private void setSelectItem(int position) {
        String category = ((Plate) objectList.get(position)).getCategory();
        for (int i = 0; i < objectList.size(); i++) {
            Object object = objectList.get(i);
            if (object instanceof Plate) {
                Plate plate = (Plate) object;
                if (plate.getCategory().equals(category)) {
                    plate.setAggregate(false);
                    objectList.set(i, plate);
                    notifyItemChanged(i);
                }
            }
        }
        ((Plate) objectList.get(position)).setAggregate(true);
        notifyItemChanged(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof Plate) {
            return 1;
        }
        return ITEM_CATEGORY;
    }

    class PlateTodayViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView tvName;
        private final ImageView ivCheck;
        private final TextView tvPrice;
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
            if (plate.getCategory().equalsIgnoreCase(TYPE_MENU_ENTRY) ||
                    plate.getCategory().equalsIgnoreCase(TYPE_MENU_DESSERT)) {
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
                plateRecyclerListener.selectItem(position);
                setSelectItem(position);
            }
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final TextView tvTitle;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        void setCategory(Category category) {
            ivImage.setImageResource(category.getImage());
            tvTitle.setText(category.getTitle());
        }
    }
}
