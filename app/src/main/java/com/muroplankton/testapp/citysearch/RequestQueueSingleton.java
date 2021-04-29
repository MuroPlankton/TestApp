package com.muroplankton.testapp.citysearch;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {
    private static RequestQueueSingleton rqSingleton;
    private RequestQueue requestQueue;
    private Context ctx;

    private RequestQueueSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized RequestQueueSingleton getRqSingleton(Context context) {
        if (rqSingleton == null) {
            rqSingleton = new RequestQueueSingleton(context);
        }
        return rqSingleton;
    }
}
