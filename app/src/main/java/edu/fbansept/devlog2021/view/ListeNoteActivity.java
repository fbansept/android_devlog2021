package edu.fbansept.devlog2021.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.controller.UtilisateurController;

public class ListeNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_note);
        init();
    }

    private void init() {
        UtilisateurController.getInstance()
                .getInformationUtilisateurConnecte(this);
    }
}