package com.example.schoolapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingletonModel {
    private static MySingletonModel mMySingletonModelInstance;
    private RequestQueue requestQueue;
    private static Context mContext;

    public MySingletonModel(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        return requestQueue;
    }

    public static synchronized MySingletonModel getInstance(Context context)
    {
        if (mMySingletonModelInstance == null)
            mMySingletonModelInstance = new MySingletonModel(context);
        return mMySingletonModelInstance;
    }

    public<T> void addToRequestQueue(Request<T> request)
    {
        requestQueue.add(request);
    }
}
