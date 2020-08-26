package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewID);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String string = sharedPreferences.getString("count", "default");
        int number = sharedPreferences.getInt("number", 2);

        textView.setText(string);
        Toast.makeText(getApplicationContext(), number+"", Toast.LENGTH_LONG).show();
    }
}
