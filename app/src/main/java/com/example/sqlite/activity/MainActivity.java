package com.example.sqlite.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    public static final int ADD_CODE = 1001;
    public static final int EDIT_CODE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _init();
        addEvent();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnuEdit:

                break;
            case R.id.mnuDelete:

                break;
            case R.id.mnuCountName:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        //inflater.inflate(R.menu.option_menu, menu); dùng để gọi menu từ file xml
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sortIncreaseName:
                Toast.makeText(this, "Sort increase name", Toast.LENGTH_SHORT).show();
                listContact.sort((o1, o2) -> {
                    int lastIndex = o1.getName().lastIndexOf(" ");
                    String lastName1 = o1.getName().substring(lastIndex + 1);
                    lastIndex = o2.getName().lastIndexOf(" ");
                    String lastName2 = o2.getName().substring(lastIndex + 1);
                    return lastName1.compareTo(lastName2);
                });
                break;
            case R.id.sortDecreaseName:
                Toast.makeText(this, "Sort decrease name", Toast.LENGTH_SHORT).show();
                listContact.sort((o1, o2) -> {
                    int lastIndex = o1.getName().lastIndexOf(" ");
                    String lastName1 = o1.getName().substring(lastIndex + 1);
                    lastIndex = o2.getName().lastIndexOf(" ");
                    String lastName2 = o2.getName().substring(lastIndex + 1);
                    return lastName2.compareTo(lastName1);
                });
                break;
        }
        adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    private void _init() {
        listContact = new ArrayList<>();
        listViewContact = findViewById(R.id.listViewContact);
        btnAddContact = findViewById(R.id.btnAddContact);
        btnDeleteContact = findViewById(R.id.btnDelete);
        adapter = new Adapter(listContact);
        addRecordToList();
        listViewContact.setAdapter(adapter);
        registerForContextMenu(listViewContact);
    }

    private void addEvent() {
        btnAddContact.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "New contact", Toast.LENGTH_SHORT).show();
            addContact();
        });
        btnDeleteContact.setOnClickListener(view -> {
            int size = adapter.getListChecked().size();
            try {
                deleteContactWithListChecked(adapter.getListChecked());
                Toast.makeText(MainActivity.this, "Remove " + size + " contact success!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException("Error remove contact!");
            }
        });
        listViewContact.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(MainActivity.this, "New contact", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        });
        listViewContact.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Toast.makeText(MainActivity.this, "New contact", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
            return false;
        });
    }

    private void addContact() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, NewActivity.class);
        startActivityForResult(intent, ADD_CODE);
    }

    private void deleteContactWithListChecked(List<Contact> listChecked) {
        for (Contact contact : listChecked) {
            listContact.remove(contact);
        }
        adapter.clearListChecked();
        adapter.notifyDataSetChanged();
    }

    private void addRecordToList() {
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van B", "0123456789", "image2"));
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van A", "0123456789", "image1"));
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van C", "0123456789", "image3"));
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van D", "0123456789", "image4"));
        listContact.add(new Contact((long) listContact.size() + 1, "Nguyen Van E", "0123456789", "image5"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CODE && resultCode == 200) {
            Intent intent = data;
            String name = intent.getStringExtra("name");
            String phone = intent.getStringExtra("phone");
            listContact.add(new Contact((long) listContact.size() + 1, name, phone, "image1"));
        } else if (requestCode == EDIT_CODE && resultCode == 200) {
            Long id = data.getLongExtra("id", 0);
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            for (Contact contact : listContact) {
                if (contact.getId() == id) {
                    contact.setName(name);
                    contact.setPhone(phone);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}