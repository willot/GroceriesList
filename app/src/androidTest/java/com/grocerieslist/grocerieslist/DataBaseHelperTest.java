package com.grocerieslist.grocerieslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;
import com.grocerieslist.grocerieslist.Data.ItemDatabaseContract;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

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

    void deleteTheDatabase(){
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


        /* Use reflection to try to run the correct constructor whenever implemented */
//        SQLiteOpenHelper dbHelper =
//                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);
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

        /* Use reflection to try to run the correct constructor whenever implemented */
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);
//        SQLiteOpenHelper dbHelper = new DataBaseHelper(mContext);

        /* Use WaitlistDbHelper to get access to a writable database */
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "test name");
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 99);
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOB");

        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = database.insert(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                testValues);

        /* If the insert fails, database.insert returns -1 */
        assertNotEquals("Unable to insert into the database", -1, firstRowId);

        /*
         * Query the database and receive a Cursor. A Cursor is the primary way to interact with
         * a database in Android.
         */
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

        /* Cursor.moveToFirst will return false if there are no records returned from your query */
        String emptyQueryError = "Error: No Records returned from waitlist query";
        assertTrue(emptyQueryError,
                wCursor.moveToFirst());

        /* Close cursor and database */
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

        assertEquals(2,wCursor.getCount());

        assertEquals("ID Autoincrement test failed!",
                firstRowId + 1, secondRowId);

    }

    @Test
    public void upgrade_database_test() throws Exception{

        SQLiteOpenHelper dbHelper = new DataBaseHelper(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "test name2");
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 994);
        testValues.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOByr");

        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = database.insert(
                ItemDatabaseContract.ItemListContract.TABLE_NAME,
                null,
                testValues);

        /* Insert ContentValues into database and get another row ID back */
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

        /* Cursor.moveToFirst will return false if there are no records returned from your query */

        assertFalse("Database doesn't seem to have been dropped successfully when upgrading",
                wCursor.moveToFirst());

        tableNameCursor.close();
        database.close();
    }

}
