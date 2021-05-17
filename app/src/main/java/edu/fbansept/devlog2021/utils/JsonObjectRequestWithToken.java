package edu.fbansept.devlog2021.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonObjectRequestWithToken extends JsonObjectRequest {

    private Context context;

    public JsonObjectRequestWithToken(Context context, int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        SharedPreferences preferences = context
                .getSharedPreferences("MesPreferences",Context.MODE_PRIVATE);

        String token = preferences.getString("token", "");

        Map<String, String> params = new HashMap<>();
        params.put("Content-Type","application/json; charset=UTF-8");
        params.put("Authorization","Bearer " + token);
        return params;
    }
}
