package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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

public class SplashScreenActivity extends AppCompatActivity {
    String strRes;
    private RequestQueue requestQueue;
    private String myUrl = "http://www.google.com";
    private boolean requestSuccess = false;

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

        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mGetResponse();
                mThreadSleep();
                if (requestSuccess)
                        mIntent();
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            textViewRetry.setVisibility(View.VISIBLE);

                            textViewRetry.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    textViewRetry.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);

                                    mGetResponse();
                                    if (requestSuccess)
                                    {
//                                        mThreadSleep();
                                        mIntent();
                                    }



                                }
                            });
                        }
                    });


                }
            }
        });
        thread.start();

    }

    private void mIntent() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    void mThreadSleep(){
        try {
            Thread.sleep(2000);
//            mIntent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void mGetResponse() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, myUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int count = 0;
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("count", ""+response.substring(0, 500));

                            for (int i =0 ; i<100000; i++)
                            {
                                count = count+i;
                            }
                            editor.putInt("number", count);
                            editor.apply();
                            requestSuccess = true;
                            Toast.makeText(SplashScreenActivity.this, "Connected", Toast.LENGTH_SHORT).show();
//                            requestQueue.stop();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SplashScreenActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                    requestQueue.stop();
                    requestSuccess = false;
                    progressBar.setVisibility(View.GONE);
                    textViewRetry.setVisibility(View.VISIBLE);
                 /*   progressBar.setVisibility(View.GONE);
                    textViewRetry.setVisibility(View.VISIBLE);
                    textViewRetry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewRetry.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            mGetResponse();

                            if (requestSuccess)
                                mIntent();
                            else
                            {
                                textViewRetry.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });*/

                }
            });
            requestQueue.add(stringRequest);


    }



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
