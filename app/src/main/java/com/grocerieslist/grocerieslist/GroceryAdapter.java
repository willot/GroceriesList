package com.grocerieslist.grocerieslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vwillot on 6/27/2017.
 */

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder>    {
    private String[] mDataset;

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView mTextView;

        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.item_text);
        }
    }

    public GroceryAdapter(String[] dataSet){
        mDataset = dataSet;
    }


    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //Todo: THIS MAYBE WRONG COME BACK TO IT
        Context context = viewGroup.getContext();
        int layoutId = R.layout.recycler_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view1  = inflater.inflate(layoutId, viewGroup, false);



//        TextView view = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

