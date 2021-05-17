package edu.fbansept.devlog2021.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {

    private int id;
    private String pseudo;
    private List<Note> listeNote = new ArrayList<>();

    public Utilisateur(JSONObject jsonUtilisateur) throws JSONException {
        id = jsonUtilisateur.getInt("id");
        pseudo = jsonUtilisateur.getString("pseudo");

        JSONArray jsonListeNote = jsonUtilisateur.getJSONArray("listeNote");

        for(int i = 0 ; i < jsonListeNote.length() ; i++) {
            JSONObject jsonNote = jsonListeNote.getJSONObject(i);

            if(jsonNote.has("texte")) {
                listeNote.add(new NoteTexte(jsonNote));
            } else {
                listeNote.add(new NoteListe(jsonNote));
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public List<Note> getListeNote() {
        return listeNote;
    }

    public void setListeNote(List<Note> listeNote) {
        this.listeNote = listeNote;
    }
}
