package com.example.contact_app_v3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contact_app_v3.Adapter.ContactAdapter;
import com.example.contact_app_v3.Model.AppDatabase;
import com.example.contact_app_v3.Model.Contact;
import com.example.contact_app_v3.Model.ContactDAO;
import com.example.contact_app_v3.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> contacts;
    private ContactAdapter contactAdapter;
    private ActivityMainBinding binding;
    private AppDatabase appDatabase;
    private ContactDAO contactDAO;

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String EMAIL = "EMAIL";
    public static final String AVATAR = "AVATAR";

    private static final int REQUEST_CODE = 2001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        appDatabase = AppDatabase.getInstance(this);
        contactDAO = appDatabase.contactDAO();

        contacts = contactDAO.getAllContacts();
        contactAdapter = new ContactAdapter(this, contacts);

        binding.rvListContact.setLayoutManager(new LinearLayoutManager(this));
        binding.rvListContact.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvListContact.setAdapter(contactAdapter);

//        contactDAO.insertAll(new Contact("Nhật Long","0905588193", "denkivip516@gmail.com", ""));
//        contactDAO.insertAll(new Contact("Trường Sanh","0123588193", "sfsdvivip516@gmail.com", ""));
//        contactDAO.insertAll(new Contact("Đình Khôi","0905123193", "denkiv123121@gmail.com", ""));
//        contactDAO.insertAll(new Contact("Như Trí","09023124193", "dendsdg6@gmail.com", ""));

//        Set Event

        binding.btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.btn_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Enter name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                contactAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            switch (resultCode){
                case CreateActivity.REQUEST_PIN:
                    String name = data.getStringExtra(NAME);
                    String phone = data.getStringExtra(PHONE);
                    String email = data.getStringExtra(EMAIL);
                    String avatar = data.getStringExtra(AVATAR);

                    contactDAO.insertAll( new Contact(name, phone, email,avatar));
                    contacts.clear();
                    contacts.addAll(contactDAO.getAllContacts());
                    contactAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "Thêm liên hệ thành công !", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}