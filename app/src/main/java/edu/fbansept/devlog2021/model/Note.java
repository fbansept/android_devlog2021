package edu.fbansept.devlog2021.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Note implements Serializable {

    private Integer id;
    private String titre;

    public Note() { }

    public Note(JSONObject jsonNote) throws JSONException {
        id = jsonNote.getInt("id");
        titre = jsonNote.getString("titre");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}