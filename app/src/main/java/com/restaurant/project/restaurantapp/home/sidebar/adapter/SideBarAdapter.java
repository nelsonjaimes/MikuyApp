package com.restaurant.project.restaurantapp.home.sidebar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.restaurant.project.restaurantapp.R;
import com.restaurant.project.restaurantapp.home.sidebar.ui.SideBarListener;

import java.util.ArrayList;
import java.util.List;

public class SideBarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> itemList;
    private Context context;
    private SideBarListener sideBarListener;

    public SideBarAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
        itemList.add(new Item(Item.HEADER_TYPE));
        itemList.add(new Item(R.drawable.menu_today, get(R.string.menu)));
        itemList.add(new Item(R.drawable.letter_dark, get(R.string.foodLetter)));
        itemList.add(new Item(R.drawable.reservation_dark, get(R.string.myReservations)));
        itemList.add(new Item(R.drawable.contacts_dark, get(R.string.contacts)));
        itemList.add(new Item(R.drawable.address_dark, get(R.string.address)));
        itemList.add(new Item(R.drawable.profileuser_dark, get(R.string.userProfile)));
    }

    private String get(int idString) {
        return context.getResources().getString(idString);
    }

    public void setSideBarListener(SideBarListener sideBarListener) {
        this.sideBarListener = sideBarListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sidebar_simple_item, parent,
                false);
        if (viewType == Item.HEADER_TYPE) {
            view = LayoutInflater.from(context).inflate(R.layout.sidebar_header_item, parent,
                    false);
            return new OtherItemViewHolder(view);
        }
        return new NormalItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        if (item.getType() == Item.NORMAL_TYPE) {
            NormalItemHolder normalItemHolder = (NormalItemHolder) holder;
            normalItemHolder.setItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    public void selectItem(int position) {
        for (Item item : itemList) {
            item.setSelect(false);
        }
        itemList.get(position).setSelect(true);
        notifyDataSetChanged();
    }

    class NormalItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivItem;
        TextView tvItem;
        RelativeLayout rlItem;
        private int position;

        NormalItemHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvItem = itemView.findViewById(R.id.tvItem);
            rlItem = itemView.findViewById(R.id.rlItem);
            rlItem.setOnClickListener(this);
        }

        void setItem(Item item) {
            tvItem.setText(item.getOption());
            ivItem.setImageResource(item.getImage());
            if (item.isSelect()) {
                changeColorItem();
            } else {
                defaultColorItem();
            }
        }

        private void changeColorItem() {
            tvItem.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            ivItem.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
        }

        private void defaultColorItem() {
            tvItem.setTextColor(context.getResources().getColor(R.color.colorDark));
            ivItem.setColorFilter(context.getResources().getColor(R.color.colorDark));
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                sideBarListener.itemSelectSideBar(position);
            }
        }
    }

    class OtherItemViewHolder extends RecyclerView.ViewHolder {
        OtherItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
