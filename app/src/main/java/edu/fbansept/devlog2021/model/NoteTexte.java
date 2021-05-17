package edu.fbansept.devlog2021.model;

import org.json.JSONException;
import org.json.JSONObject;

public class NoteTexte extends Note{

    private String texte;

    public NoteTexte(JSONObject jsonNoteTexte) throws JSONException {
        super(jsonNoteTexte);
        texte = jsonNoteTexte.getString("texte");
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
