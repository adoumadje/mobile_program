package com.example.odev6contactssqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewContactActivity extends AppCompatActivity {

    EditText etACFirstName, etACLastName, etACPhoneNumber;
    Button btnAddContact;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        setTitle("Add New Contact");

        etACFirstName = findViewById(R.id.et_ACFirstName);
        etACLastName = findViewById(R.id.et_ACLastName);
        etACPhoneNumber = findViewById(R.id.et_ACPhoneNumber);
        databaseHelper = new DatabaseHelper(NewContactActivity.this);
        btnAddContact = findViewById(R.id.btn_AddContact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
            }
        });
    }

    private void addContact() {
        String firstname = etACFirstName.getText().toString();
        String lastname = etACLastName.getText().toString();
        String phoneNumber = etACPhoneNumber.getText().toString();
        if(!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname)
                && !TextUtils.isEmpty(phoneNumber)) {
            boolean success = databaseHelper.addNewContact(firstname,lastname,phoneNumber);
            if(success) {
                Toast.makeText(NewContactActivity.this,
                        "New contact added successfully", Toast.LENGTH_SHORT).show();
                freeInputs();
            } else {
                Toast.makeText(NewContactActivity.this,
                        "Error adding new contact", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(NewContactActivity.this,
                    "No field can be left empty!", Toast.LENGTH_SHORT).show();
        }
    }

    private void freeInputs() {
        etACFirstName.setText("");
        etACLastName.setText("");
        etACPhoneNumber.setText("");
    }
}