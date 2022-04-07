package com.example.odev6contactssqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContactActivity extends AppCompatActivity {

    EditText etUCFirstName, etUCLastName, etUCPhoneNumber;
    Button btnUpdateContact;
    DatabaseHelper databaseHelper;
    int id, phoneNumber;
    String firstname, lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        setTitle("Update Contact");

        etUCFirstName = findViewById(R.id.et_UCFirstName);
        etUCLastName = findViewById(R.id.et_UCLastName);
        etUCPhoneNumber = findViewById(R.id.et_UCPhoneNumber);
        Intent intent = getIntent();
        setContactInfos(intent);

        databaseHelper = new DatabaseHelper(UpdateContactActivity.this);
        btnUpdateContact = findViewById(R.id.btn_UpdateContact);
        btnUpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateContact();
            }
        });
    }

    private void setContactInfos(Intent intent) {
        id = intent.getIntExtra("ID", 0);
        firstname = intent.getStringExtra("FIRSTNAME");
        lastname = intent.getStringExtra("LASTNAME");
        phoneNumber = intent.getIntExtra("PHONE_NUMBER", 0);
        String phoneNumStr = String.valueOf(phoneNumber);

        etUCFirstName.setText(firstname);
        etUCLastName.setText(lastname);
        etUCPhoneNumber.setText(phoneNumStr);
    }

    private void updateContact() {
        firstname = etUCFirstName.getText().toString();
        lastname = etUCLastName.getText().toString();
        phoneNumber = Integer.parseInt(etUCPhoneNumber.getText().toString());
        int result = databaseHelper.updateContact(id, firstname, lastname, phoneNumber);
        if(result != 0) {
            Toast.makeText(UpdateContactActivity.this,
                    "Contact successfully updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UpdateContactActivity.this,
                    "Error updating contact", Toast.LENGTH_SHORT).show();
        }
    }
}