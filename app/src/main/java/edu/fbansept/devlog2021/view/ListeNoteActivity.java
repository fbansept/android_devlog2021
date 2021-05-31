package edu.fbansept.devlog2021.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.controller.UtilisateurController;
import edu.fbansept.devlog2021.view.adapter.ListeNoteAdapter;

public class ListeNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListeNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_note);
        init();
    }

    private void init() {

        recyclerViewListeNote = findViewById(R.id.recyclerView_listeNote);
        recyclerViewListeNote.setLayoutManager(new LinearLayoutManager(this));

        UtilisateurController.getInstance()
                .getInformationUtilisateurConnecte(this, utilisateur -> {
                    recyclerViewListeNote.setAdapter(
                            new ListeNoteAdapter(
                                    utilisateur.getListeNote(),
                                    note -> {
                                        Intent intent = new Intent(
                                                this,
                                                EditionNoteTexteActivity.class);

                                        intent.putExtra("note", note);
                                        startActivity(intent);
                                    }));
                });
    }
}