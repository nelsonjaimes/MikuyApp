package com.restaurant.project.mikuyapp.contacts.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.contacts.model.ItemContacts;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ConctactsViewHolder> {
    public interface ContactsListener {
        void selectItem(int pos);
    }

    private Context context;
    private List<ItemContacts> itemContactsList;
    private ContactsListener contactsListener;

    public ContactsAdapter(Context context) {
        this.context = context;
    }

    public void setItemContactsList(List<ItemContacts> itemContactsList) {
        this.itemContactsList = itemContactsList;
    }

    public void setContactsListener(ContactsListener contactsListener) {
        this.contactsListener = contactsListener;
    }

    @NonNull
    @Override
    public ConctactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConctactsViewHolder(LayoutInflater.from(context).
                inflate(R.layout.row_contacts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConctactsViewHolder holder, int position) {
        holder.setContact(itemContactsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemContactsList.size();
    }

    class ConctactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivImage;
        private TextView tvDescription;
        int position;

        ConctactsViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            itemView.setOnClickListener(this);
        }

        void setContact(ItemContacts contact) {
            ivImage.setImageResource(contact.getImage());
            tvDescription.setText(contact.getDescription());
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                contactsListener.selectItem(position);
            }
        }
    }
}
