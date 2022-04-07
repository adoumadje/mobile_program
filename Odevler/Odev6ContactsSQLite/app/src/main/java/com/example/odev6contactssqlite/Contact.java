package com.example.odev6contactssqlite;

public class Contact {
    public int id;
    public String firstname;
    public String lastname;
    public int phoneNumber;

    public Contact(int id, String firstname, String lastname, int phoneNumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }
}
