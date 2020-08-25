package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {
    private static final String TAG = "My Tracing : ";
    String strRes;
//    private RequestQueue requestQueue;
    private String myUrl = "http://www.google.com";
    private boolean requestSuccess;

    private ProgressBar progressBar;
    private TextView textViewRetry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        //hide statusBar | NavigationBar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //End
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.launcherProgressBarId);
        textViewRetry = findViewById(R.id.txV_retry);

        textViewRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRetry.setVisibility(View.GONE);
                mGetResponse();
            }
        });

       //1. User er data ache kina check kora, jodi thake taile hander er code run e third Activity te jaoar code kore dite hove.
        //2. jodi na thake,, taile getResponse call

        boolean hasUserData = false;

        if (hasUserData)
        {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: into the Handler ");
                    mIntent(RegistrationActivity.class);
                }
            }, 3000);
        }else {
            mGetResponse();
        }
        Log.d(TAG, "onCreate: After initializing handler");
    }


    private void mIntent(Class target) {
        Intent intent = new Intent(SplashScreenActivity.this, target);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void mGetResponse() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, myUrl, this, this);
            progressBar.setVisibility(View.VISIBLE);

        MySingletonModel.getInstance(SplashScreenActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        textViewRetry.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(String response) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", response.substring(0, 500));
        editor.apply();

        progressBar.setVisibility(View.GONE);
        mIntent(RegistrationActivity.class);
    }

/*    private Response.Listener successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {


        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };*/

    /*    private void mProgressBarLoad() {
        for (progress = 0 ; progress<100; progress++)
        {
            try {
                Thread.sleep(50);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }*/

}
