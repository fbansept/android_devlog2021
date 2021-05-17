package edu.fbansept.devlog2021.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoteListe extends Note {

    private List<Tache> listeTache;

    public NoteListe(JSONObject jsonNoteListe) throws JSONException {
        super(jsonNoteListe);

        this.listeTache = new ArrayList<>();

        JSONArray jsonListeTache = jsonNoteListe.getJSONArray("listeTache");

        for(int i = 0 ; i < jsonListeTache.length() ; i ++) {
            JSONObject jsonTache = jsonListeTache.getJSONObject(i);
            listeTache.add(new Tache(jsonTache));
        }
    }

    public List<Tache> getListeTache() {
        return listeTache;
    }

    public void setListeTache(List<Tache> listeTache) {
        this.listeTache = listeTache;
    }
}
