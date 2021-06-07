package edu.fbansept.devlog2021.model;

import org.json.JSONException;
import org.json.JSONObject;

public class NoteTexte extends Note{

    private String texte;

    public NoteTexte() {
        super();
    }

    public NoteTexte(JSONObject jsonNoteTexte) throws JSONException {
        super(jsonNoteTexte);
        texte = jsonNoteTexte.getString("texte");
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonNote = new JSONObject();

        jsonNote.put("id", this.getId());
        jsonNote.put("titre", this.getTitre());
        jsonNote.put("texte", this.getTexte());

        return jsonNote;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }


}
