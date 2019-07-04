package com.example.xoomcodechallenge.service;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyQueue {
    private static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue(final Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
}
