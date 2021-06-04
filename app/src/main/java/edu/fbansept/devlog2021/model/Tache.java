package edu.fbansept.devlog2021.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Tache implements Serializable {

    private Integer id;
    private boolean termine;
    private String texte;

    public Tache() {
    }

    public Tache(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        texte = jsonObject.getString("texte");
        termine = jsonObject.getBoolean("termine");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isTermine() {
        return termine;
    }

    public void setTermine(boolean termine) {
        this.termine = termine;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
