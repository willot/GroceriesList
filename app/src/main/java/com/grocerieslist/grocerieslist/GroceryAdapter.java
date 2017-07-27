package com.grocerieslist.grocerieslist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grocerieslist.grocerieslist.Data.ItemDatabaseContract;

import java.util.Random;

/**
 * Created by vwillot on 6/27/2017.
 */

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder>    {
    private Cursor mCursor;

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView mTextView;

        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.item_text);
        }
    }

    public GroceryAdapter(Cursor cursor){
        mCursor = cursor;
    }


    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.recycler_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(layoutId, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        int rgb = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        viewHolder.itemView.setBackgroundColor(rgb);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryAdapter.ViewHolder holder, int position) {
           if( mCursor.moveToPosition(position)) {
               String itemName = mCursor.getString(mCursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME));
               holder.mTextView.setText(itemName);
           }
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }
}

