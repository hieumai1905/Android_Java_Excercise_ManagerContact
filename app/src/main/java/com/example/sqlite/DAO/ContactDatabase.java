package com.example.sqlite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "SQLite_database";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "db_contact";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";

   public ContactDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
   }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            String sql = String.format("create table [if not exists] %s(%s INTEGER PRIMARY KEY, %s TINYTEXT NOT NULL, %s TINYTEXT NOT NULL);"
                    , TABLE_NAME, KEY_ID, KEY_NAME, KEY_PHONE);
            sqLiteDatabase.execSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error create table " + TABLE_NAME);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Contact getContact(Long id){
       SQLiteDatabase db = this.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE % = %d", TABLE_NAME, KEY_ID, id);
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        Contact contact = new Contact(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
        cursor.close();
        return contact;
    }

    public List<Contact> getAllContact(){
        SQLiteDatabase db  = this.getReadableDatabase();
        List<Contact> listContact = new ArrayList<Contact>();
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null) {
            while(cursor.moveToNext()){
               listContact.add(new Contact(cursor.getLong(0), cursor.getString(1), cursor.getString(2)));
            }
            return listContact;
        }
        return null;
    }

    public void updateContact(Contact contact){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues values = new ContentValues();
       values.put(KEY_NAME, contact.getName());
       values.put(KEY_PHONE, contact.getPhone());
        db.update(TABLE_NAME, values, KEY_ID +" = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public void removeContact(Long id){
       SQLiteDatabase db = this.getReadableDatabase();
       db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
