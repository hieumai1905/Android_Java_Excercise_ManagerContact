package com.example.sqlite.service.contact;

import android.content.Context;

import com.example.sqlite.DAO.ContactDatabase;
import com.example.sqlite.DAO.IHandlerDatabse;
import com.example.sqlite.model.Contact;

import java.util.List;

public class ContactService implements IContactService {
    private IHandlerDatabse<Contact, Long> contactDatabase;
    private static ContactService _instance;

    private ContactService(Context context) {
        contactDatabase = ContactDatabase.getInstance(context);
    }

    public static ContactService getInstanceContactService(Context context){
        if(_instance == null){
            _instance = new ContactService(context);
        }return _instance;
    }

    @Override
    public boolean add(Contact contact) {
        try{
            contactDatabase.addContact(contact);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = null;
        try{
            contacts = contactDatabase.getAllContact();
        }catch(Exception e){
            throw new RuntimeException("Error at find all!");
        }
        return contacts;
    }

    @Override
    public Contact find(Long id) {
        Contact contact = null;
        try{
            contact = contactDatabase.getContact(id);
        }catch(Exception e){
            throw new RuntimeException("Error at find");
        }
        return contact;
    }

    @Override
    public boolean update(Contact contact) {
        try{
            contactDatabase.updateContact(contact);
        }catch(Exception e){
            throw new RuntimeException("Error at update");
        }
        return true;
    }

    @Override
    public boolean remove(Long id) {
        try{
            contactDatabase.removeContact(id);
        }catch(Exception e){
            throw new RuntimeException("Error at update");
        }
        return true;
    }
}
