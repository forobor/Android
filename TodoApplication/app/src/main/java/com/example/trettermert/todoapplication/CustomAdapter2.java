package com.example.trettermert.todoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.trettermert.todoapplication.R.layout.projectlist_cell;

/**
 * Created by Trettermert on 17.09.2017.
 */

public class CustomAdapter2 extends ArrayAdapter<String> {

    private LayoutInflater mInflater;
    private static int selectedPosition = 0;
    private final String[] titles;

    public CustomAdapter2(Context context, String[] titles) {
        super(context, R.layout.projectlist_cell, titles);
        this.titles = titles;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public static int getSelectedId(){
        return selectedPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View rowView = convertView;
        if (rowView == null) {
            rowView = mInflater.inflate(R.layout.projectlist_cell, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.label);
            holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.textView.setText(titles[position]);
        if (selectedPosition == position){
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        return rowView;
    }
    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

    public static void setSelectedPosition(int i, CustomAdapter2 adapter){
        selectedPosition = i;
        adapter.notifyDataSetChanged();
    }
}
