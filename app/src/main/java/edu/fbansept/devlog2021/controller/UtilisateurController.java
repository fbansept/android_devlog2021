package edu.fbansept.devlog2021.controller;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.model.Utilisateur;
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

    public void getInformationUtilisateurConnecte(Context context) {

        JsonObjectRequestWithToken request = new JsonObjectRequestWithToken(
                context,
                Request.Method.GET,
                context.getResources().getString(R.string.url_spring) + "user/utilisateur-connecte",
                null,
                jsonUtilisateur -> {
                    try {
                        Utilisateur utilisateur = new Utilisateur(jsonUtilisateur);
                        System.out.println(utilisateur);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                erreur -> System.out.println("erreur")
        );

        RequestManager.getInstance(context).addToRequestQueue(request);
    }
}
