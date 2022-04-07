package com.example.odev6contactssqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "CONTACTS.DB";
    public final static String TABLE_NAME = "CONTACTS";
    public final static String COL_ID = "ID";
    public final static String COL_FIRSTNAME = "FIRSTNAME";
    public final static String COL_LASTNAME = "LASTNAME";
    public final static String COL_PHONE_NUMBER = "PHONE_NUMBER";

    SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_QUERY = "CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY, FIRSTNAME TEXT NOT NULL,"+
                " LASTNAME TEXT NOT NULL, PHONE_NUMBER INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
        this.database = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String UPGRADE_QUERY = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(UPGRADE_QUERY);
        this.onCreate(sqLiteDatabase);
    }

    public ArrayList<Contact> listAllContacts() {
        this.database = getReadableDatabase();
        ArrayList<Contact> contactsList = new ArrayList<>();
        Contact contact;
        int id, phoneNumber;
        String firstname, lastname;
        String QUERY = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = database.rawQuery(QUERY,null);
        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                firstname = cursor.getString(1);
                lastname = cursor.getString(2);
                phoneNumber = cursor.getInt(3);
                contact = new Contact(id,firstname,lastname,phoneNumber);
                contactsList.add(contact);
            } while (cursor.moveToNext());
        }
        database.close();
        return contactsList;
    }

    public boolean addNewContact(String firstname, String lastname, String phoneNumberStr) {
        this.database = getWritableDatabase();
        String QUERY = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = database.rawQuery(QUERY,null);
        int id = cursor.getCount();
        int phoneNumber = Integer.parseInt(phoneNumberStr);

        ContentValues values = new ContentValues();
        values.put(COL_ID, id+1);
        values.put(COL_FIRSTNAME, firstname);
        values.put(COL_LASTNAME, lastname);
        values.put(COL_PHONE_NUMBER, phoneNumber);

        Long result = database.insert(TABLE_NAME, null, values);
        database.close();
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int deleteContact(Contact selectedContact) {
        int id = selectedContact.id;
        this.database = getWritableDatabase();
        int result = database.delete(TABLE_NAME, "ID=?", new String[]{ String.valueOf(id) });
        database.close();
        return result;
    }

    public int updateContact(int id, String firstname, String lastname, int phoneNumber) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_FIRSTNAME, firstname);
        values.put(COL_LASTNAME, lastname);
        values.put(COL_PHONE_NUMBER, phoneNumber);

        this.database = getWritableDatabase();
        int result = database.update(TABLE_NAME, values, "ID=?", new String[]{ String.valueOf(id) });
        database.close();
        return result;
    }
}
