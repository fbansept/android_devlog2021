package edu.fbansept.devlog2021.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.io.Serializable;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.model.NoteTexte;

public class EditionNoteTexteActivity extends AppCompatActivity {

    EditText editTextTitre;
    EditText editTextContenu;
    NoteTexte note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_note_texte);
        init();
    }

    private void init() {

        note = (NoteTexte)getIntent().getSerializableExtra("note");

        editTextTitre = findViewById(R.id.editText_titreNoteTexte);
        editTextContenu = findViewById(R.id.editText_contenuNoteTexte);
        editTextTitre.setText(note.getTitre());
        editTextContenu.setText(note.getTexte());
    }
}