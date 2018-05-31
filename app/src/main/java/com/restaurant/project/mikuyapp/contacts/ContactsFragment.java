package com.restaurant.project.mikuyapp.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.contacts.adapter.ContactsAdapter;
import com.restaurant.project.mikuyapp.contacts.model.ItemContacts;
import com.restaurant.project.mikuyapp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements ContactsAdapter.ContactsListener {

    final int ITEM_WEB = 2;
    private Context context;
    final int ITEM_PHONE = 0;
    final int ITEM_EMAIL = 3;
    final int ITEM_FACEBOOK = 1;
    private RecyclerView rvContacts;

    public static ContactsFragment getInstance() {
        return new ContactsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvContacts = view.findViewById(R.id.rvContacts);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<ItemContacts> contactsList = new ArrayList<>();
        contactsList.add(new ItemContacts(R.drawable.ic_celu_64, Constant.PHONE_NUMBER));
        contactsList.add(new ItemContacts(R.drawable.ic_face_64, Constant.ACCOUNT_FACEBOOK));
        contactsList.add(new ItemContacts(R.drawable.ic_destokc_64, Constant.URL_WEB));
        contactsList.add(new ItemContacts(R.drawable.ic_email_64, Constant.EMAIL));
        ContactsAdapter contactsAdapter = new ContactsAdapter(context);
        contactsAdapter.setItemContactsList(contactsList);
        contactsAdapter.setContactsListener(this);
        rvContacts.setAdapter(contactsAdapter);
    }

    @Override
    public void selectItem(int pos) {
        switch (pos) {
            case ITEM_PHONE:
                navigationCallPhone();
                break;
            case ITEM_FACEBOOK:
                navigationProfileFacebook();
                break;
            case ITEM_WEB:
                navigationWeb(Constant.URL_WEB);
                break;
            case ITEM_EMAIL:
                navigationSedEmail();
                break;
        }
    }

    private void navigationWeb(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    private void navigationProfileFacebook() {
        try {
            navigationWeb(Constant.PAGE_ID_FACEBOOK);
        } catch (Exception e) {
            navigationWeb(Constant.URL_FACEBOOK);
        }
    }

    private void navigationCallPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(Constant.FORMAT_PHONE_NUMBER));
        startActivity(intent);
    }

    private void navigationSedEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", Constant.EMAIL, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subjectSendEmail));
        context.startActivity(Intent.createChooser(emailIntent, getString(R.string.titleDialogSend)));
    }
}

