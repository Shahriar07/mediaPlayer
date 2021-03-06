package com.shahriar.surahshikkha.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shahriar.surahshikkha.R;

import java.util.ArrayList;

/**
 * Created by H. M. Shahriar on 2/24/2018.
 */

public class SortDialogItemAdapter extends RecyclerView.Adapter {

    public void setItem(ArrayList<String> itemList) {
        this.itemList = itemList;
    }

    private ArrayList<String> itemList;
    Context context;
    int selectedItem;
    int offset;
    LayoutInflater inflater;
    public SortDialogItemAdapter( Context context, ArrayList<String> itemList, int selectedItem, int offset) {
        Log.d(getClass().getSimpleName(), " Item List Size "+itemList.size());
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.selectedItem = selectedItem;
        this.offset = offset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.sort_item_layout, parent, false);
        ItemHolder vh = new ItemHolder(v);
        //Log.d(getClass().getSimpleName(),"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.bindItem(itemList.get(position),position);
        Log.d(getClass().getSimpleName(),"onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



class ItemHolder extends RecyclerView.ViewHolder {
    public TextView itemTextView;

    public ItemHolder(View v) {
        super(v);
        itemTextView = (TextView) v.findViewById(R.id.dialog_list_item);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.bangla_font);
        itemTextView.setTypeface(typeface);
    }

    public void bindItem(String item, int position){

        itemTextView.setText(item);
        if (position + offset == selectedItem){
            itemTextView.setBackgroundResource(R.color.selected_verse_background);
        }
        else {
            itemTextView.setBackgroundResource(android.R.color.transparent);
        }
    }
}
}
