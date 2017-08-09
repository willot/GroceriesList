package com.grocerieslist.grocerieslist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;
import com.grocerieslist.grocerieslist.Data.ItemDatabaseContract;

import java.util.ArrayList;

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

        new ItemTouchHelper(new SimpleCallback(ItemTouchHelper.DOWN|ItemTouchHelper.UP, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                mDataBaseHelper.deleteItem(id);
                mAdapter.refreshCursorItem(mDataBaseHelper.getAllItems());
            }
        }).attachToRecyclerView(mRecyclerView);

    }

    private void addNewItemToDatabase(String itemName){

        ContentValues imputContentValue = new ContentValues();
        imputContentValue.put(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME, itemName);
        imputContentValue.put(ItemDatabaseContract.ItemListContract.COLUMN_QUANTITY, 2);
        imputContentValue.put(ItemDatabaseContract.ItemListContract.COLUMN_TYPE, "BOB");
        mDataBaseHelper.createItem(imputContentValue);
    }

    public void addItemToDatabase(View view){
        String itemName = mNewItemEditText.getText().toString();
        if(mNewItemEditText.getText().length() == 0){
            return;
        }

        addNewItemToDatabase(itemName);

        mAdapter.refreshCursorItem(mDataBaseHelper.getAllItems());

        mNewItemEditText.getText().clear();
        mNewItemEditText.clearFocus();
    }

    public void sendEmail(View view){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"vianney.willot@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Grocery List");



        ArrayList<String> itemList = new ArrayList<String>();
        String newline = System.getProperty("line.separator");
        String emailBody =getResources().getString(R.string.first_line_email) + newline;


        Cursor cursorAllItems = mDataBaseHelper.getAllItems();

        if(cursorAllItems != null){
            while (cursorAllItems.moveToNext()){
                String itemName = cursorAllItems.getString(cursorAllItems.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME));
                itemList.add(itemName);
            }
        }

        for (String string:itemList) {
            emailBody = emailBody + string + newline;
        }

        intent.putExtra(Intent.EXTRA_TEXT   , emailBody);
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }







}
