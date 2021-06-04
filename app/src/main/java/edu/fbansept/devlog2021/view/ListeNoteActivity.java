package edu.fbansept.devlog2021.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.controller.UtilisateurController;
import edu.fbansept.devlog2021.model.Note;
import edu.fbansept.devlog2021.model.NoteTexte;
import edu.fbansept.devlog2021.view.adapter.ListeNoteAdapter;

public class ListeNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListeNote;
    private FloatingActionButton floatingActionButton;

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
                                    noteCliquee -> {
                                        Intent intent = new Intent(
                                                this,
                                                noteCliquee instanceof NoteTexte
                                                    ? EditionNoteTexteActivity.class
                                                    : EditionNoteListeActivity.class);

                                        intent.putExtra("note", noteCliquee);
                                        startActivity(intent);
                                    }));
                });

        floatingActionButton = findViewById(R.id.floatingActionButton_listeNote);

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    this,
                    EditionNoteTexteActivity.class);

            intent.putExtra("note", new NoteTexte());
            startActivity(intent);
        });


    }
}