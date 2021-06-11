package edu.fbansept.devlog2021.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.Request;

import org.json.JSONException;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.model.Utilisateur;
import edu.fbansept.devlog2021.utils.FileRequest;
import edu.fbansept.devlog2021.utils.JsonObjectRequestWithToken;
import edu.fbansept.devlog2021.utils.RequestManager;

public final class UtilisateurController {

    private static UtilisateurController instance = null;

    private UtilisateurController() {
    }

    public static UtilisateurController getInstance() {

        if(instance == null) {
            instance = new UtilisateurController();
        }

        return instance;
    }


    public interface UtilisateurConnecteListener {
        void onUtilisateurConnecteListener(Utilisateur utilisateur);
    }

    public void getInformationUtilisateurConnecte(Context context, UtilisateurConnecteListener listener) {

        JsonObjectRequestWithToken request = new JsonObjectRequestWithToken(
                context,
                Request.Method.GET,
                context.getResources().getString(R.string.url_spring) + "user/utilisateur-connecte",
                null,
                jsonUtilisateur -> {
                    try {
                        Utilisateur utilisateur = new Utilisateur(jsonUtilisateur);
                        listener.onUtilisateurConnecteListener(utilisateur);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                erreur -> System.out.println("erreur")
        );

        RequestManager.getInstance(context).addToRequestQueue(request);
    }

    public interface TelechargementImageProfilUtilisateur{
        void onTelechargementImageProfilUtilisateur(Bitmap image);
    }

    public void getImageProfilUtilisateur(
            Context context,
            TelechargementImageProfilUtilisateur ecouteur) {

        FileRequest request = new FileRequest(
                Request.Method.GET,
                context.getResources().getString(R.string.url_spring)
                        + "test/image-resource",
                bytes -> {
                    Bitmap image = BitmapFactory
                            .decodeByteArray(bytes,0,bytes.length);
                    ecouteur.onTelechargementImageProfilUtilisateur(image);
                },
                erreur -> erreur.printStackTrace()
        );

        RequestManager.getInstance(context).addToRequestQueue(request);
    }
}
