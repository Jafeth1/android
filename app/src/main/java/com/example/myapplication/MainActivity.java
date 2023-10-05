package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref = null;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);

        pref = getSharedPreferences("username", MODE_PRIVATE);
        editText = findViewById(R.id.editText);
        String savedName = pref.getString("userName","");
        editText.setText(savedName);
        Intent nextPage = new Intent(this, NameActivity.class);
        button.setOnClickListener(click ->
        {
            String name = editText.getText().toString();
            SharedPreferences.Editor edit = pref.edit();
            edit.putString("username", name);
            edit.apply();
            nextPage.putExtra("username", name);
            startActivityForResult(nextPage, 1);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();

        String currentName = editText.getText().toString();
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("username", currentName);
        edit.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}