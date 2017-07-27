package com.grocerieslist.grocerieslist.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.grocerieslist.grocerieslist.Models.ItemsToSave;

import java.util.ArrayList;


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

    public long createItem(ContentValues contentValues){
        SQLiteDatabase database = this.getWritableDatabase();
        long id = database.insert(ItemDatabaseContract.ItemListContract.TABLE_NAME, null, contentValues);

        database.close();

        return id;
    }

    public int deleteItem(long itemId){
        SQLiteDatabase database = this.getWritableDatabase();
        String itemIdToString = String.valueOf(itemId);
        String[] argsId = {itemIdToString};
        int numberRowDeleted = database.delete(ItemDatabaseContract.ItemListContract.TABLE_NAME, ItemDatabaseContract.ItemListContract._ID + "= ?", argsId);
        database.close();
        return numberRowDeleted;
    }

//    public ArrayList<ItemsToSave> getAllItems() {
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.query(ItemDatabaseContract.ItemListContract.TABLE_NAME, null, null, null, null, null, null);
//        ArrayList<ItemsToSave> itemList = new ArrayList<ItemsToSave>();
//        cursor.getCount();
//        if(cursor.moveToFirst()){
//            while (cursor.moveToNext()) {
//                String quantityString = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY));
//                String name = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME));
//                String type = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_TYPE));
//                int quantity = Integer.parseInt(quantityString);
//                ItemsToSave itemsToSave = new ItemsToSave(quantity, name, type);
//                itemList.add(itemsToSave);
//            }
//        }
//        database.close();
//        return itemList;
//    }

    public Cursor getAllItems() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(ItemDatabaseContract.ItemListContract.TABLE_NAME, null, null, null, null, null, null);
//        if(cursor.moveToFirst()){
//            while (cursor.moveToNext()) {
//                String quantityString = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY));
//                String name = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME));
//                String type = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_TYPE));
//                int quantity = Integer.parseInt(quantityString);
//                ItemsToSave itemsToSave = new ItemsToSave(quantity, name, type);
//                itemList.add(itemsToSave);
//            }
//        }
//        database.close();
        return cursor;
    }
}
