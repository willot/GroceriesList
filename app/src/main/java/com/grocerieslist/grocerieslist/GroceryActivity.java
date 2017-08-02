package com.grocerieslist.grocerieslist;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;
import com.grocerieslist.grocerieslist.Data.ItemDatabaseContract;

public class GroceryActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GroceryAdapter mAdapter;
    EditText mNewItemEditText;
    DataBaseHelper mDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mNewItemEditText = (EditText) findViewById(R.id.new_item);

        mDataBaseHelper = new DataBaseHelper(this);

        Cursor cursor = mDataBaseHelper.getAllItems();
        mAdapter = new GroceryAdapter(cursor);
        mRecyclerView.setAdapter(mAdapter);


    }

    void addNewItemToDatabase(String itemName){

        ContentValues imputContentValue = new ContentValues();
        imputContentValue.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, itemName);
        imputContentValue.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 2);
        imputContentValue.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOB");
        mDataBaseHelper.createItem(imputContentValue);
    }

    void AddItemToDatabase(View view){
        String itemName = mNewItemEditText.getText().toString();
        if(itemName == ""){
            return;
        }

        addNewItemToDatabase(itemName);

        mAdapter.refreshCursorItem(mDataBaseHelper.getAllItems());

        mNewItemEditText.getText().clear();
        mNewItemEditText.clearFocus();


    }


}
