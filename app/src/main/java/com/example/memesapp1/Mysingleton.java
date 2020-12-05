package com.example.memesapp1;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingleton {
    private static Mysingleton instance;
    private RequestQueue requestQueue;

    private static Context ctx;

    private Mysingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();


    }

    public static synchronized Mysingleton getInstance(Context context) {
        if (instance == null) {
            instance = new Mysingleton(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}