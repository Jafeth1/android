package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CharacterDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        String characterName = getIntent().getStringExtra("characterName");
        TextView textView = findViewById(R.id.characterDetailsTextView);
        textView.setText(characterName);
    }
}