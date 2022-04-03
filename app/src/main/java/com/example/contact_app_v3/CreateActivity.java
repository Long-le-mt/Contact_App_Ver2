package com.example.contact_app_v3;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contact_app_v3.Model.AppDatabase;
import com.example.contact_app_v3.Model.Contact;
import com.example.contact_app_v3.Model.ContactDAO;
import com.example.contact_app_v3.databinding.ActivityCreateContactBinding;


public class CreateActivity extends AppCompatActivity {
    private ActivityCreateContactBinding binding;
    private AppDatabase appDatabase;
    private ContactDAO contactDAO;
    Uri img;
    public static final int REQUEST_PIN = 2002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Contact Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);

        appDatabase = AppDatabase.getInstance(this);
        contactDAO = appDatabase.contactDAO();

        binding.btnAddImg.setOnClickListener(v -> mGetContent.launch("image/*"));
    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        binding.imgPerson.setImageURI(result);
                        img = result;

                        Toast.makeText(CreateActivity.this, result.toString() , Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_save) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.NAME, binding.edName.getText().toString());
                    intent.putExtra(MainActivity.EMAIL, binding.edPhone.getText().toString());
                    intent.putExtra(MainActivity.PHONE, binding.edEmail.getText().toString());
                    intent.putExtra(MainActivity.AVATAR, String.valueOf(img));

                    setResult(REQUEST_PIN, intent);
                    finish();
                }
            });
            return true;
        } else if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}