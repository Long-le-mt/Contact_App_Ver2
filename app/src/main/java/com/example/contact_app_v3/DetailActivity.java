package com.example.contact_app_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contact_app_v3.Adapter.ContactAdapter;
import com.example.contact_app_v3.Model.Contact;
import com.example.contact_app_v3.databinding.ActivityContactDetailBinding;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ActivityContactDetailBinding binding;
    private Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactDetailBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Contact Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setGui();
    }

    public void setGui(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        contact = (Contact) bundle.get(ContactAdapter.CONTACT);
        binding.twName.setText(contact.getName().toString());
        binding.twPhone.setText(contact.getPhone().toString());
        if(!contact.getAvatar().isEmpty()){
            Picasso.get().load(contact.getAvatar()).fit().into(binding.imgPerson);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.btn_edit:
                Intent intent = new Intent(this, EditActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(ContactAdapter.CONTACT, contact);
                intent.putExtras(bundle);

                startActivity(intent);
                return true;
            case R.id.btn_more:
                Toast.makeText(DetailActivity.this, "Btn more", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}