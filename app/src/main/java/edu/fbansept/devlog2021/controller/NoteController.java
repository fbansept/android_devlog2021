package edu.fbansept.devlog2021.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.model.NoteTexte;
import edu.fbansept.devlog2021.utils.RequestManager;
import edu.fbansept.devlog2021.utils.StringRequestWithToken;
import edu.fbansept.devlog2021.view.LoginActivity;

public final class NoteController {

    private static NoteController instance = null;

    private NoteController() {
    }

    public static NoteController getInstance() {

        if(instance == null) {
            instance = new NoteController();
        }

        return instance;
    }

    public interface Save {
        void onSave(String urlNote);
    }

    public void save(Context context, NoteTexte note, Save saveListener) throws JSONException {

        JSONObject jsonBody = note.toJson();

        StringRequestWithToken request = new StringRequestWithToken(
                context,
                Request.Method.POST,
                context.getResources().getString(R.string.url_spring) + "user/noteTexte",
                urlNote -> {
                    saveListener.onSave(urlNote);
                },
                messageErreur -> {
                    System.out.println(messageErreur);
                }
        ){
            @Override
            public byte[] getBody() throws AuthFailureError {
                 return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestManager.getInstance(context).addToRequestQueue(request);

    }

}
