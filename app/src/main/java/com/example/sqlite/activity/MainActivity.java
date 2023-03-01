package com.example.sqlite.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sqlite.R;
import com.example.sqlite.adapter.Adapter;
import com.example.sqlite.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> listContact;
    private Button btnAddContact;
    public static Button btnDeleteContact;
    private Adapter adapter;
    private ListView listViewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _init();
        addEvent();
    }

    private void _init() {
        listContact = new ArrayList<>();
        listViewContact = findViewById(R.id.listViewContact);
        btnAddContact = findViewById(R.id.btnAddContact);
        btnDeleteContact = findViewById(R.id.btnDelete);
        adapter = new Adapter(listContact);
        addRecordToList();
        listViewContact.setAdapter(adapter);
    }

    private void addEvent() {
        btnAddContact.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "New contact", Toast.LENGTH_SHORT).show();
        });
        btnDeleteContact.setOnClickListener(view -> {
            int size = adapter.getListChecked().size();
            try {
                deleteContactWithListChecked(adapter.getListChecked());
                Toast.makeText(MainActivity.this, "Remote " + size + " contact success!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException("Error remote contact!");
            }
        });
    }

    private void deleteContactWithListChecked(List<Contact> listChecked) {
        for (Contact contact : listChecked) {
            listContact.remove(contact);
        }
        adapter.clearListChecked();
        adapter.notifyDataSetChanged();
    }

    private void addRecordToList() {
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van A", "0123456789", "image1"));
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van B", "0123456789", "image2"));
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van C", "0123456789", "image3"));
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van D", "0123456789", "image4"));
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van E", "0123456789", "image5"));
    }


}