package edu.fbansept.devlog2021.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.controller.NoteController;
import edu.fbansept.devlog2021.model.NoteListe;
import edu.fbansept.devlog2021.model.NoteTexte;
import edu.fbansept.devlog2021.view.adapter.ListeTacheEditableAdapter;

public class EditionNoteListeActivity extends AppCompatActivity {

    EditText editTextTitre;
    NoteListe note;
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_note_liste);
        init();
    }

    private void init() {

        note = (NoteListe) getIntent().getSerializableExtra("note");

        recyclerView = findViewById(R.id.recyclerView_editionListeTache);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListeTacheEditableAdapter(note.getListeTache()));

        editTextTitre = findViewById(R.id.editText_titreNoteListe);
        editTextTitre.setText(note.getTitre());

        bottomAppBar = findViewById(R.id.bottomAppBar_editionNoteListe);

        bottomAppBar.setOnMenuItemClickListener(menuItem -> {

            if(menuItem.getItemId() == R.id.menuItem_supprimer) {
                Log.d("event","bouton supprimé cliqué");
                return true;
            }

            return false;
        });

        floatingActionButton = findViewById(R.id.fab_editionNoteListe);

        floatingActionButton.setOnClickListener(v -> {

            note.setTitre(editTextTitre.getText().toString());

        });


    }
}