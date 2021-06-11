package edu.fbansept.devlog2021.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.model.Note;
import edu.fbansept.devlog2021.model.NoteTexte;
import edu.fbansept.devlog2021.utils.ImageUpload;
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

    public void save(Context context, Note note, Save saveListener) throws JSONException {

        String url = "user/" + (note instanceof NoteTexte ? "noteTexte" : "noteListe");

        JSONObject jsonBody = note.toJson();

        StringRequestWithToken request = new StringRequestWithToken(
                context,
                Request.Method.POST,
                context.getResources().getString(R.string.url_spring) + url,
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

    public void ajoutImageNote(Context context, Uri uri) throws IOException {
        ImageUpload imageUpload = new ImageUpload(
                context,
                uri,
                context.getResources()
                        .getString(R.string.url_spring) + "test/image-upload",
                message -> {},
                erreur -> erreur.printStackTrace()
        );
        imageUpload.setRetryPolicy(
                new DefaultRetryPolicy(
                        50000,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestManager.getInstance(context).addToRequestQueue(imageUpload);
    }

}
