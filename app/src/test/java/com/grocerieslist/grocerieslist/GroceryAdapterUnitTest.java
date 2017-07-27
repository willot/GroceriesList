package com.grocerieslist.grocerieslist;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Created by vwillot on 6/29/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GroceryAdapterUnitTest {


    @Test
    public void testGetItemCountIsNotZero(){

        Cursor mockCursor = Mockito.mock(Cursor.class);
        when(mockCursor.getCount()).thenReturn(2);


        GroceryAdapter groceryAdapter = new GroceryAdapter(mockCursor);

        int expectedItemCount = groceryAdapter.getItemCount();
        assertEquals(expectedItemCount, 2);
    }

    @Test
    public void testGetItemCountIsZero(){

        Cursor mockCursor = Mockito.mock(Cursor.class);

        GroceryAdapter groceryAdapter = new GroceryAdapter(mockCursor);

        int expectedItemCount = groceryAdapter.getItemCount();
        assertEquals(expectedItemCount, 0);
    }

}
