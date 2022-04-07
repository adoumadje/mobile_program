package com.example.odev6contactssqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ContactsListActivity extends AppCompatActivity {
    
    ListView lvContactsList;
    DatabaseHelper databaseHelper;
    List<Contact> contactsList;
    Contact selectedContact;
    int CALL_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        setTitle("Contacts List");

        databaseHelper = new DatabaseHelper(ContactsListActivity.this);
        lvContactsList = findViewById(R.id.lv_ContactsList);
        lvContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedContact = contactsList.get(i);
            }
        });
        renderContactsList();
    }

    private void renderContactsList() {
        contactsList = databaseHelper.listAllContacts();
        List<String> contactsNamesList = new ArrayList<>();
        String name;
        for(Contact contact: contactsList) {
            name = contact.firstname + " " + contact.lastname;
            contactsNamesList.add(name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, contactsNamesList);
        lvContactsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_Call:
                call(selectedContact);
                break;
            case R.id.menu_UpdateContact:
                updateContact(selectedContact);
                break;
            case R.id.menu_DeleteContact:
                deleteContact(selectedContact);
                break;
            case R.id.menu_NewContact:
                addNewContact();
                break;
            case R.id.menu_Exit:
                quitApp();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void quitApp() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void addNewContact() {
        Intent intent = new Intent(ContactsListActivity.this,
                NewContactActivity.class);
        startActivity(intent);
    }

    private void deleteContact(Contact selectedContact) {
        if(selectedContact == null) {
            Toast.makeText(ContactsListActivity.this,
                    "Please select contact", Toast.LENGTH_SHORT).show();
        } else {
            int result = databaseHelper.deleteContact(selectedContact);
            if(result != 0) {
                Toast.makeText(ContactsListActivity.this,
                        "Contact successfully deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ContactsListActivity.this,
                        "Error deleting contact", Toast.LENGTH_SHORT).show();
            }
            renderContactsList();
        }
    }

    private void updateContact(Contact selectedContact) {
        if(selectedContact == null) {
            Toast.makeText(ContactsListActivity.this,
                    "Please select contact", Toast.LENGTH_SHORT).show();
        } else {
            int id = selectedContact.id;
            String firstname = selectedContact.firstname;
            String lastname = selectedContact.lastname;
            int phoneNumber = selectedContact.phoneNumber;
            Intent intent = new Intent(ContactsListActivity.this,
                    UpdateContactActivity.class);
            intent.putExtra("ID", id);
            intent.putExtra("FIRSTNAME", firstname);
            intent.putExtra("LASTNAME", lastname);
            intent.putExtra("PHONE_NUMBER", phoneNumber);
            startActivity(intent);
        }
    }

    private void call(Contact selectedContact) {
        if(selectedContact == null) {
            Toast.makeText(ContactsListActivity.this,
                    "Please select contact", Toast.LENGTH_SHORT).show();
        } else {
            String phoneNumber = String.valueOf(selectedContact.phoneNumber);
            if(ActivityCompat.checkSelfPermission(ContactsListActivity.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                makeCall(phoneNumber);
            } else {
                if(ActivityCompat.shouldShowRequestPermissionRationale(ContactsListActivity.this,
                        Manifest.permission.CALL_PHONE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactsListActivity.this);
                    builder.setTitle("Info");
                    builder.setMessage("This application needs CALL PERMISSION to make calls");
                    builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                ActivityCompat.requestPermissions(ContactsListActivity.this,
                        new String[] { Manifest.permission.CALL_PHONE }, CALL_REQUEST_CODE);
            }
        }
    }

    private void makeCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String phoneNumber = String.valueOf(selectedContact.phoneNumber);
                makeCall(phoneNumber);
            }
        }
    }
}