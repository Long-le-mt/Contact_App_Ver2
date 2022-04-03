package com.example.contact_app_v3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact_app_v3.DetailActivity;
import com.example.contact_app_v3.MainActivity;
import com.example.contact_app_v3.Model.Contact;
import com.example.contact_app_v3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements Filterable {
    private List<Contact> contactList;
    private List<Contact> contactListByFilter;
    private Context mContext;

    public static final String CONTACT = "CONTACT";

    public ContactAdapter(Context mContext, List<Contact> contactList) {
        this.mContext = mContext;
        this.contactList = contactList;
        this.contactListByFilter = new ArrayList<Contact>(contactList);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        if (contact == null)
            return;

        String name = contact.getName().toString();

        holder.twName.setText(name);
        if(name != null && !name.isEmpty()){
            holder.twWordPrefix.setText(name.substring(0, 1));
            holder.twWordPrefixNotBg.setText(name.substring(0, 1));
        }

//        if(!contact.getAvatar().isEmpty()){
//            Log.e("DEBUG", contact.getAvatar());
////            holder.imgPerson.setImageURI(Uri.parse(contact.getAvatar()));
////            holder.twWordPrefix.setText("");
//            Picasso.get().load(contact.getAvatar()).fit().into(holder.imgPerson);
//        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(CONTACT, contact);
                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (contactList == null)
            return 0;
        return contactList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @NonNull
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Contact> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(contactListByFilter);
            }else{
                for (Contact contact : contactListByFilter){
                    if(contact.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(contact);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contactList.clear();
            contactList.addAll((Collection<? extends Contact>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView twName, twWordPrefix, twWordPrefixNotBg;
        private ImageView imgPerson;
        private RelativeLayout relativeLayout;
        private LinearLayout linearLayout;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            twName = (TextView) itemView.findViewById(R.id.tw_name);
            twWordPrefix = (TextView) itemView.findViewById(R.id.tw_word_prefix);
            twWordPrefixNotBg = (TextView) itemView.findViewById(R.id.tw_word_prefix_not_bg);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_parent);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_item);
            imgPerson = (ImageView) itemView.findViewById(R.id.img_person);
        }
    }
}
