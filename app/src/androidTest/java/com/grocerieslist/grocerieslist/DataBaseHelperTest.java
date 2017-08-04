package com.grocerieslist.grocerieslist;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;
import com.grocerieslist.grocerieslist.Data.ItemDatabaseContract;
import com.grocerieslist.grocerieslist.Models.ItemsToSave;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by vwillot on 7/6/2017.
 */

public class DataBaseHelperTest {

    private final Context mContext = InstrumentationRegistry.getTargetContext();
    private final Class mDbHelperClass = DataBaseHelper.class;

    @Before
    public void setUp() {
        deleteTheDatabase();
    }

    public void deleteTheDatabase(){
        try {
            /* Use reflection to get the database name from the db helper class */
            Field f = mDbHelperClass.getDeclaredField("DATABASE_NAME");
            f.setAccessible(true);
            mContext.deleteDatabase((String)f.get(null));
        }catch (NoSuchFieldException ex){
            fail("Make sure you have a member called DATABASE_NAME in the WaitlistDbHelper");
        }catch (Exception ex){
            fail(ex.getMessage());
        }

    }

      @Test
    public void create_database_test() throws Exception{

        SQLiteOpenHelper dbHelper = new DataBaseHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String databaseIsNotOpen = "The database should be open and isn't";
        assertEquals(databaseIsNotOpen,
                true,
                database.isOpen());

        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
                        ItemDatabaseContract.ItemListContract.TABLE_NAME + "'",
                null);

        String errorInCreatingDatabase =
                "Error: This means that the database has not been created correctly";
        assertTrue(errorInCreatingDatabase,
                tableNameCursor.moveToFirst());

        assertEquals("Error: Your database was created without the expected tables.",
                ItemDatabaseContract.ItemListContract.TABLE_NAME, tableNameCursor.getString(0));

        tableNameCursor.close();
    }

    @Test
    public void insert_single_record_test() throws Exception{

        SQLiteOpenHelper dbHelper = new DataBaseHelper(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "test name");
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 99);
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOB");

        long firstRowId = database.insert(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                testValues);

        /* If the insert fails, database.insert returns -1 */
        assertNotEquals("Unable to insert into the database", -1, firstRowId);

        Cursor wCursor = database.query(
                /* Name of table on which to perform the query */
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        assertEquals(1,wCursor.getCount());

        String emptyQueryError = "Error: No Records returned from waitlist query";
        assertTrue(emptyQueryError,
                wCursor.moveToFirst());

        wCursor.close();
        dbHelper.close();
    }

    @Test
    public void autoincrement_test() throws Exception{

        SQLiteOpenHelper dbHelper = new DataBaseHelper(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "test name2");
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 994);
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOBy");

        long firstRowId = database.insert(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                testValues);

        long secondRowId = database.insert(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                testValues);

        Cursor wCursor = database.query(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        assertEquals(2,wCursor.getCount());

        assertEquals("ID Autoincrement test failed!",
                firstRowId + 1, secondRowId);

        wCursor.close();
        dbHelper.close();

    }

    @Test
    public void upgrade_database_test() throws Exception{

        SQLiteOpenHelper dbHelper = new DataBaseHelper(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "test name2");
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 994);
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOByr");

        long firstRowId = database.insert(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                testValues);

        long secondRowId = database.insert(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                testValues);

        dbHelper.onUpgrade(database, 0, 1);
        database = dbHelper.getReadableDatabase();

        /* This Cursor will contain the names of each table in our database */
        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
                        ItemDatabaseContract.ItemListContract.TABLE_NAME + "'",
                null);

        assertTrue(tableNameCursor.getCount() == 1);

        Cursor wCursor = database.query(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        /* Cursor.moveToFirst will return false if there are no records returned from your query */

        assertFalse("Database doesn't seem to have been dropped successfully when upgrading",
                wCursor.moveToFirst());

        tableNameCursor.close();
        database.close();
    }

    @Test
    public void createItemTest(){
        DataBaseHelper dbHelper = new DataBaseHelper(mContext);

        ContentValues testValues = new ContentValues();
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "test name2");
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 994);
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOByr");
        long itemId = dbHelper.createItem(testValues);

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor wCursor = database.query(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        assertTrue(wCursor.getCount() == 1);

        assertNotEquals("Unable to insert into the database", -1, itemId);
        assertEquals(1,itemId);

        database.close();
    }


    @Test
    public void deleteItemTest(){
        DataBaseHelper dbHelper = new DataBaseHelper(mContext);

        ContentValues testValues = new ContentValues();
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "test name2");
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 994);
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOByr");
        long itemId1 = dbHelper.createItem(testValues);
        long itemId2 = dbHelper.createItem(testValues);

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor wCursor = database.query(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        assertTrue(wCursor.getCount() == 2);
        database.close();

        int numberRowDeleted = dbHelper.deleteItem(itemId1);
        assertEquals(1,numberRowDeleted);

        SQLiteDatabase database2 = dbHelper.getReadableDatabase();

        Cursor wCursor2 = database2.query(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        assertTrue(wCursor2.getCount() == 1);
        database2.close();
    }
    @Test
    public void getAllItemTest(){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);

        ContentValues testValues1 = new ContentValues();
        testValues1.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "item1");
        testValues1.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 2);
        testValues1.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOB");

        ContentValues testValues2 = new ContentValues();
        testValues2.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "item2");
        testValues2.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 4);
        testValues2.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOB");

        long itemId1 = dataBaseHelper.createItem(testValues1);
        long itemId2 = dataBaseHelper.createItem(testValues2);

        Cursor cursor = dataBaseHelper.getAllItems();

        assertEquals(2, cursor.getCount());
        ArrayList<ItemsToSave> itemList = new ArrayList<ItemsToSave>();

        if(cursor.moveToFirst()){
            ItemsToSave itemsToSave = createItem(cursor);
            itemList.add(itemsToSave);
            while (cursor.moveToNext()) {
                itemsToSave = createItem(cursor);
                itemList.add(itemsToSave);
            }
        }

        assertEquals(2,itemList.size());

        assertEquals("item2", itemList.get(1).getItemName());
        assertEquals(4, itemList.get(1).getQuantity());
        assertEquals("BOB", itemList.get(1).getType());


        assertEquals("item1", itemList.get(0).getItemName());
        assertEquals(2, itemList.get(0).getQuantity());
        assertEquals("BOB", itemList.get(0).getType());

        deleteTheDatabase();

    }

    @NonNull
    private ItemsToSave createItem(Cursor cursor) {
        String quantityString = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY));
        String name = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME));
        String type = cursor.getString(cursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_TYPE));
        int quantity = Integer.parseInt(quantityString);
        return new ItemsToSave(quantity, name, type);
    }

}
