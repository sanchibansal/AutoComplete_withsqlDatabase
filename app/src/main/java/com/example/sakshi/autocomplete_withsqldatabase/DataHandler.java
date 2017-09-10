package com.example.sakshi.autocomplete_withsqldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.data;

/**
 * Created by sakshi on 9/10/2017.
 */

public class DataHandler extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME="DataBase";        //create database name
    public static  final String TABLE_NAME="Products";       //create table name
    public static  final String KEY_NAME="product_name";                  //col2
    private Context context;
    public static  final int DATABASE_VERSON=1;             //optional
    SQLiteDatabase sqLiteDatabase;
    public DataHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSON);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(Id INTEGER PRIMARY KEY AUTOINCREMENT, product_name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);

    }
    public boolean insert_Data(String name)
    {
        SQLiteDatabase db=getWritableDatabase();            //to write data into the table
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,name);             //putting the values into ContentValue
        long res=db.insert(TABLE_NAME,null,values);     //Create a temp variable
        if(res==-1)
        {
            return  false;      //if data is not inserted return false
        }
        else
        {
            return  true;           //else return true
        }
    }
    public Cursor showProduct() {
        //fetching data
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Products", null);
        if (cursor != null)
            return cursor;
        else
            return null;
    }
}
