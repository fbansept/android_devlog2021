package edu.fbansept.devlog2021.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final class RequestManager {

    private static RequestManager instance = null;
    private Context context;
    private RequestQueue requestQueue;

    private RequestManager(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static RequestManager getInstance(Context context) {

        if(instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public void addToRequestQueue(Request request) {
        getRequestQueue().add(request);
    }
}
