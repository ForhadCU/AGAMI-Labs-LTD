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
        int number = sharedPreferences.getInt("number", 0);

        textView.setText(number+"");
        Toast.makeText(getApplicationContext(), string+"", Toast.LENGTH_LONG).show();
    }
}
