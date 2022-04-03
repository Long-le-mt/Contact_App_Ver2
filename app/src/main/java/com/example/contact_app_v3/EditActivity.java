package com.example.contact_app_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contact_app_v3.Adapter.ContactAdapter;
import com.example.contact_app_v3.Model.AppDatabase;
import com.example.contact_app_v3.Model.Contact;
import com.example.contact_app_v3.Model.ContactDAO;
import com.example.contact_app_v3.databinding.ActivityCreateContactBinding;
import com.example.contact_app_v3.databinding.ActivityEditContactBinding;

public class EditActivity extends AppCompatActivity {
    private ActivityEditContactBinding binding;
    private Contact contact;
    private AppDatabase appDatabase;
    private ContactDAO contactDAO;

    public static final int REQUEST_PIN_EDIT = 2003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditContactBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Contact");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setGui();

        appDatabase = AppDatabase.getInstance(this);
        contactDAO = appDatabase.contactDAO();
    }

    public void setGui(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        contact = (Contact) bundle.get(ContactAdapter.CONTACT);
        binding.edName.setText(contact.getName().toString());
        binding.edPhone.setText(contact.getPhone().toString());
        binding.edEmail.setText(contact.getEmail().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
            return true;
        }else if(id == R.id.btn_save){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    contact.setName(binding.edName.getText().toString());
                    contact.setPhone(binding.edPhone.getText().toString());
                    contact.setEmail(binding.edEmail.getText().toString());
//                    contact.getAvatar(binding)
                    contactDAO.update(contact);

                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}