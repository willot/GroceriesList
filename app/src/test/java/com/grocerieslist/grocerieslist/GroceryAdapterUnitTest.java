package com.grocerieslist.grocerieslist;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContext;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;
import com.grocerieslist.grocerieslist.Data.ItemDatabaseContract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
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

    @Test
    public void testOnBindViewHolderSetTextOnView(){
        Cursor mockCursor = Mockito.mock(Cursor.class);
        when(mockCursor.getCount()).thenReturn(1);
        when(mockCursor.moveToPosition(1)).thenReturn(true);
        when(mockCursor.getString(mockCursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME))).thenReturn("BOB!!!!");

        View mockView = Mockito.mock(View.class);
        Resources mockAssetManager = Mockito.mock(Resources.class);







        Context mockContext = Mockito.mock(Context.class);
        when(mockContext.getResources()).thenReturn(mockAssetManager);

        TextView mockTextView = new MockTextView(mockContext);

        GroceryAdapter.ViewHolder mockViewHolder = new GroceryAdapter.ViewHolder(mockView);
        mockViewHolder.setTextView(mockTextView);



//        MockTextView spy = Mockito.spy(new MockTextView(mockContext));

        // Prevent/stub logic in super.save()
//        Mockito.doNothing().when((AppCompatTextView)spy);



        GroceryAdapter groceryAdapter = new GroceryAdapter(mockCursor);

        groceryAdapter.onBindViewHolder(mockViewHolder,1);

//        Mockito.verify( mockViewHolder.mTextView, Mockito.times(1)).setText("BOB!!!!");
        assertEquals(mockViewHolder.mTextView, "BOB!!!!");

    }

    class MockTextView extends android.support.v7.widget.AppCompatTextView {


        public MockTextView(Context context) {
            super(context);
        }

        public void setText(String text){
            text = text;
        }
    }


}
