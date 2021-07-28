package it.xabaras.android.recyclerview.swipedecorator.sample;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paolo Montalto on 12/06/17.
 * Copyright (c) 2017 Paolo Montalto. All rights reserved.
 */

class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleRecyclerViewAdapter.ViewHolder> {
    private static String TAG = "ADAPTER";
    private Context context;
    private List<String> mItems;

    public SampleRecyclerViewAdapter(Context context) {
        reloadItems();
        this.context = context;
    }

    public void reloadItems() {
        try {
            mItems = new ArrayList<>();
            mItems.add("Row 1");
            mItems.add("Row 2");
            mItems.add("Row 3");
            mItems.add("Row 4");
            mItems.add("Row 5");
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.view_list_item, parent, false);
            vh = new ViewHolder(view);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.text1.setText(mItems.get(position));
            holder.text2.setText("Swipe left or right to see what happens");
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItem(String item, int position) {
        try {
            mItems.add(position, item);
            notifyItemInserted(position);
        } catch(Exception e) {
            Log.e("MainActivity", e.getMessage());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
        }
    }

    public String removeItem(int position) {
        String item = null;
        try {
            item = mItems.get(position);
            mItems.remove(position);
            notifyItemRemoved(position);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return item;
    }
}
