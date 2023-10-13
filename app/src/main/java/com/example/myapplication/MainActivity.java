package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Switch urgentSwitch;
    private Button addButton;
    private ListView listView;
    private List<TodoItem> todoList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);

        todoList = new ArrayList<>();
        todoAdapter = new TodoAdapter(this, todoList);
        listView.setAdapter(todoAdapter);

        addButton.setOnClickListener(v -> {
            String text = editText.getText().toString();
            boolean urgent = urgentSwitch.isChecked();

            TodoItem newItem = new TodoItem(text, urgent);
            todoList.add(newItem);

            editText.getText().clear();
            todoAdapter.notifyDataSetChanged();
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Do you want to delete this?");
            builder.setMessage("The selected row is: " + position);

            builder.setPositiveButton("Yes", (dialog, which) -> {
                todoList.remove(position);
                todoAdapter.notifyDataSetChanged();
            });

            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

            builder.show();
            return true;
        });
    }
}
