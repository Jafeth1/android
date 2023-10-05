package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        TextView text = findViewById(R.id.textView3);

        Intent dataSent = getIntent();
        String nameSent = dataSent.getStringExtra("username");

        text.setText("Welcome " + nameSent);

        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button2.setOnClickListener(click -> {
            setResult(0);
            finish();
        });

        button3.setOnClickListener(click -> {
            setResult(1);
            System.exit(1);
        });
    }
}
