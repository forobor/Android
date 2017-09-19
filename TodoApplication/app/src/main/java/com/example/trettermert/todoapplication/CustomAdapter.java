package com.example.trettermert.todoapplication;

import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

class CustomAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<Project> projects = new ArrayList<Project>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public CustomAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item, int id) {
        projects.get(id).addTodo(item);
        notifyDataSetChanged();
    }

    public void addItem(final String item, final boolean isCompleted) {
        projects.get(projects.size()-1).addTodo(item, isCompleted);
        notifyDataSetChanged();
    }



    public void addSectionHeaderItem(final String item) {
        projects.add(new Project(item));
        sectionHeader.add(getCount()-1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (int i = 0; i < projects.size(); i++) {
            count+= 1+ projects.get(i).getTodosCount();
        }
        return count;
    }

    @Override
    public String getItem(int position) {
        int count =0;
        for(int i=0; i<projects.size(); i++){
            if (position==count)
                return projects.get(i).getTitle();
            count++;
            for(int j=0; j < projects.get(i).getTodosCount(); j++){
                if (position==count)
                    return projects.get(i).getTodoAtIndex(j).getText();
                count++;
            }
        }
        return "";
    }

    public boolean getIsCompleted(int position) {
        int count =0;
        for(int i=0; i<projects.size(); i++){
            count++;
            for(int j=0; j < projects.get(i).getTodosCount(); j++){
                if (position==count)
                    return projects.get(i).getTodoAtIndex(j).isCompleted();
                count++;
            }
        }
        return false;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = new ViewHolder();
        int rowType = getItemViewType(position);
        final int pos = position;


        switch (rowType) {
            case TYPE_ITEM:
                convertView = mInflater.inflate(R.layout.snippet_item1, null);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (holder.checkBox.isChecked()) {
                            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            return;
                        } else {
                            holder.textView.setPaintFlags(0);
                        }
                    }
                });
                holder.checkBox.setChecked(getIsCompleted(position));
                break;
            case TYPE_SEPARATOR:
                convertView = mInflater.inflate(R.layout.snippet_item2, null);
                holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                break;
        }
        convertView.setTag(holder);

        holder.textView.setText(getItem(position));

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }

}