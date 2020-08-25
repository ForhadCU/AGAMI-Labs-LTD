package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView textView = findViewById(R.id.txtV_regAct);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        textView.setText(sharedPreferences.getString("data", "def"));

    }
}
