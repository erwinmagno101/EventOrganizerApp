package com.example.eventorganizerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Users.db";

    public DBHelper(Context context) {
        super(context, "Users.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table caterer(username TEXT primary key, password TEXT, firstname TEXT, lastname TEXT, businessname TEXT, address TEXT, insta TEXT, fb TEXT, contact TEXT, bio TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists caterer");


    }

    public boolean insertCatererData(String username, String password, String firstname, String lastname, String businessname, String address, String insta, String fb, String contact, String bio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);
        values.put("firstname", firstname);
        values.put("lastname", lastname);
        values.put("businessname", businessname);
        values.put("address", address);
        values.put("insta", insta);
        values.put("fb", fb);
        values.put("contact", contact);
        values.put("bio", bio);

        long result = db.insert("caterer", null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkCatererUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from caterer where username=?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkCatererLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from caterer where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getCatererData(String username) {

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from caterer where email=?", new String[]{username});
        return cursor;
    }

    public Boolean updateCatererData(String username, String password, String insta, String fb, String contact, String bio) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        contentValues.put("insta", insta);
        contentValues.put("fb", fb);
        contentValues.put("contact", contact);
        contentValues.put("bio", bio);
        Cursor cursor = DB.rawQuery("select * from caterer where username=?", new String[]{username});

        if (cursor.getCount() > 0) {
            long result = DB.update("caterer", contentValues, "username=?", new String[]{username});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}