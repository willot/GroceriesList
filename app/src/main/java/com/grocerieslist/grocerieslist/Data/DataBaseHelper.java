package com.grocerieslist.grocerieslist.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by vwillot on 7/5/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reminder_Database.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold waitlist data
        final String SQL_CREATE_ITEMLIST_TABLE = "CREATE TABLE " + ItemDatabaseContract.ItemListContract.TABLE_NAME + " (" +
                ItemDatabaseContract.ItemListContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY + " TEXT NOT NULL, " +
                ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME + " INTEGER NOT NULL, " +
                ItemDatabaseContract.ItemListContract.COLUMN_TYPE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_ITEMLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItemDatabaseContract.ItemListContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
