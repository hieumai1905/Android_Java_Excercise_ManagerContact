package com.example.sqlite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite.R;


public class NewActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;
    private EditText txtName;
    private EditText txtPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_contact);
        int requestCode = getIntent().getIntExtra("requestCode", 1001);
        _init(requestCode);
        addEvent(requestCode);
    }

    private void _init(int requestCode) {
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        txtName = findViewById(R.id.inputName);
        txtPhone = findViewById(R.id.inputPhone);
        if (requestCode == MainActivity.EDIT_CODE) {
            Intent intent = getIntent();
            txtName.setText(intent.getStringExtra("name"));
            txtPhone.setText(intent.getStringExtra("phone"));
        }
    }

    private void addEvent(int requestCode) {
        btnSave.setOnClickListener(v -> {
            if (setResultData(requestCode))
                finish();
        });
        btnCancel.setOnClickListener(v -> {
            Toast.makeText(NewActivity.this, "Cancel add contact!", Toast.LENGTH_SHORT).show();
            finishFromChild(NewActivity.this);
        });
    }

    private boolean setResultData(int requestCode) {
        Intent intent = getIntent();
        String name = txtName.getText().toString();
        String phone = txtPhone.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(NewActivity.this, "Name is null!", Toast.LENGTH_SHORT).show();
            txtName.requestFocus();
            return false;
        } else if (phone.isEmpty()) {
            Toast.makeText(NewActivity.this, "Phone is null!", Toast.LENGTH_SHORT).show();
            txtPhone.requestFocus();
            return false;
        } else {
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            if (requestCode == MainActivity.EDIT_CODE) {
                intent.putExtra("id", getIntent().getLongExtra("id", 0));
            }
            setResult(200, intent);
        }
        return true;
    }
}
