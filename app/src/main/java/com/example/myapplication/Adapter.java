package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter extends ArrayAdapter<TodoItem> {

    private List<TodoItem> itemList;

    public Adapter(Context context, List<TodoItem> itemList) {
        super(context, 0, itemList);
        this.itemList = itemList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.todo_item, null);

        TextView textView = view.findViewById(R.id.textView);
        TodoItem currentItem = itemList.get(position);

        textView.setText(currentItem.getText());

        if (currentItem.isUrgent()) {
            view.setBackgroundColor(Color.RED);
            textView.setTextColor(Color.WHITE);
        }

        return view;
    }
}
