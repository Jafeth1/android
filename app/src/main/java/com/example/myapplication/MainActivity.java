package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Database databaseHelper;
    private SQLiteDatabase db;
    private EditText editText;
    private Switch urgentSwitch;
    private Button addButton;
    private ListView listView;
    private List<TodoItem> todoList;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);

        databaseHelper = new Database(this);
        db = databaseHelper.getWritableDatabase();
        loadTodosFromDB();

        adapter = new Adapter(this, todoList);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String text = editText.getText().toString();
            boolean urgent = urgentSwitch.isChecked();

            if (!text.isEmpty()) {
                addTodoToDB(text, urgent);
                todoList.add(new TodoItem(text, urgent));
                adapter.notifyDataSetChanged();
                editText.getText().clear();
            }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Do you want to delete this?");
            builder.setMessage("The selected row is: " + position);

            builder.setPositiveButton("Yes", (dialog, which) -> {
                TodoItem selectedTodo = todoList.get(position);
                deleteTodoFromDB(selectedTodo.getText());
                todoList.remove(position);
                adapter.notifyDataSetChanged();
            });

            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
            return true;
        });
    }

    private void loadTodosFromDB() {
        Cursor cursor = db.query(Database.TABLE_NAME, null, null, null, null, null, null);
        todoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String todo = cursor.getString(cursor.getColumnIndex(Database.COLUMN_TEXT));
            @SuppressLint("Range") int urgentValue = cursor.getInt(cursor.getColumnIndex(Database.COLUMN_URGENT));
            boolean isUrgent = (urgentValue == 1);
            TodoItem todoItem = new TodoItem(todo, isUrgent);
            todoList.add(todoItem);
        }
        printCursor(cursor);
        cursor.close();
    }

    private void addTodoToDB(String todo, boolean urgent) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_TEXT, todo);
        values.put(Database.COLUMN_URGENT, urgent ? 1 : 0);
        db.insert(Database.TABLE_NAME, null, values);
    }


    private void deleteTodoFromDB(String todo) {
        db.delete(Database.TABLE_NAME, Database.COLUMN_TEXT + " = ?", new String[]{todo});
    }

    @SuppressLint("Range")
    private void printCursor(Cursor c) {
        Log.d("DB INFO", "Database Version: " + db.getVersion());
        Log.d("DB INFO", "Number of Columns: " + c.getColumnCount());
        Log.d("DB INFO", "Column Names: " + java.util.Arrays.toString(c.getColumnNames()));
        Log.d("DB INFO", "Number of Results: " + c.getCount());

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.d("DB INFO", c.getString(c.getColumnIndex(Database.COLUMN_TEXT)));
            c.moveToNext();
        }
    }
}
