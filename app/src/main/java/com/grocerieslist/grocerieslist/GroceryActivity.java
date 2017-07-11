package com.grocerieslist.grocerieslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

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
        mAdapter = new GroceryAdapter(dataSet);
        mRecyclerView.setAdapter(mAdapter);


    }


}
