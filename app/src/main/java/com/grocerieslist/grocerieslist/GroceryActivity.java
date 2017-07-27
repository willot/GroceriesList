package com.grocerieslist.grocerieslist;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;
import com.grocerieslist.grocerieslist.Data.ItemDatabaseContract;

public class GroceryActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GroceryAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] dataSet ={"BOB","BUS","BIP","BOP","BUP","BAB","BIP","BRR","BYP","BAS","BRI","BOP","BOP","BTB","BWP","BZR","BAP","BQS","BDI"};
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        ContentValues testValues1 = new ContentValues();
        testValues1.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, "item3");
        testValues1.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 2);
        testValues1.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOB");

        dataBaseHelper.createItem(testValues1);
//        dataBaseHelper.createItem(testValues1);
//        dataBaseHelper.createItem(testValues1);
//        dataBaseHelper.createItem(testValues1);
//        dataBaseHelper.createItem(testValues1);
//        dataBaseHelper.createItem(testValues1);
//        dataBaseHelper.createItem(testValues1);


        Cursor cursor = dataBaseHelper.getAllItems();
        mAdapter = new GroceryAdapter(cursor);
        mRecyclerView.setAdapter(mAdapter);


    }


}
