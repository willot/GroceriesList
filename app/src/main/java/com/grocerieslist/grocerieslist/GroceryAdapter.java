package com.grocerieslist.grocerieslist;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.preference.PreferenceManager;
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
    private String mBackGroundColorCell;

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView mTextView;

        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.item_text);
        }

        public void setTextView(TextView textView){
            mTextView = textView;
        };
    }

    public GroceryAdapter(Cursor cursor, Context context){
        mCursor = cursor;
        getBackgorundColorFromSharedPreferences(context);
    }


    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.recycler_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(layoutId, viewGroup, false);

//        getBackgorundColorFromSharedPreferences(context);


        ViewHolder viewHolder = new ViewHolder(view);

        generateProperBackgroundColor(context, viewHolder);

        return viewHolder;
    }

    private void generateProperBackgroundColor(Context context, ViewHolder viewHolder) {
        if(mBackGroundColorCell.equals("random") || mBackGroundColorCell.equals("") ){
            int rgb = Color.rgb(new Random().nextInt(250)+5, new Random().nextInt(250)+5, new Random().nextInt(250)+5);
            viewHolder.itemView.setBackgroundColor(rgb);
        }
        else{

            switch (mBackGroundColorCell){
                case "green":
                    viewHolder.itemView.setBackgroundColor(Color.GREEN);
                    break;
                case "yellow":
                    viewHolder.itemView.setBackgroundColor(Color.YELLOW);
                    break;
                case "blue":
                    viewHolder.itemView.setBackgroundColor(Color.BLUE);
                    break;
                case "fushia":
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorFuchsia));
            }
        }
    }

    private void getBackgorundColorFromSharedPreferences(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mBackGroundColorCell = defaultSharedPreferences.getString("list_color", "random");
    }

    @Override
    public void onBindViewHolder(GroceryAdapter.ViewHolder holder, int position) {


           if( mCursor.moveToPosition(position)) {
               String itemName = mCursor.getString(mCursor.getColumnIndex(ItemDatabaseContract.ItemListContract.COLUMN_ITEM_NAME));

               long id = mCursor.getInt(mCursor.getColumnIndex(ItemDatabaseContract.ItemListContract._ID));
               holder.itemView.setTag(id);

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

    public void refreshCursorItem(Cursor cursor){
        if(mCursor !=null){
            mCursor.close();
        }
        mCursor = cursor;

        if(mCursor !=null){
            this.notifyDataSetChanged();
        }
    }

}

