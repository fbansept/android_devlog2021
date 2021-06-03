package edu.fbansept.devlog2021.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.Serializable;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.controller.NoteController;
import edu.fbansept.devlog2021.model.NoteTexte;

public class EditionNoteTexteActivity extends AppCompatActivity {

    EditText editTextTitre;
    EditText editTextContenu;
    NoteTexte note;
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;

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

        bottomAppBar = findViewById(R.id.bottomAppBar_editionNoteTexte);

        bottomAppBar.setOnMenuItemClickListener(menuItem -> {

            if(menuItem.getItemId() == R.id.menuItem_ajouterFichier) {
                System.out.println("bouton ajout fichier cliqué");
                return true;
            } else if(menuItem.getItemId() == R.id.menuItem_ajouterPhoto) {
                System.out.println("bouton ajout photo cliqué");
                return true;
            } else if(menuItem.getItemId() == R.id.menuItem_ajouterPosition) {
                System.out.println("bouton ajout position cliqué");
                return true;
            } else if(menuItem.getItemId() == R.id.menuItem_supprimer) {
                System.out.println("bouton supprimé cliqué");
                return true;
            }

            return false;
        });

        floatingActionButton = findViewById(R.id.fab_editionNoteTexte);

        floatingActionButton.setOnClickListener(v -> {
            //on change les propriétés de l'objet note avec les valeur des champs de saisie
            note.setTitre(editTextTitre.getText().toString());
            note.setTexte(editTextContenu.getText().toString());

            //on envoie l'objet note au controleur pour le sauvegarder
            try {
                NoteController.getInstance().save(
                        this,
                        note,
                        urlNote -> {
                            System.out.println("note sauvegardé");
                        });
            } catch (JSONException e) {
                Toast.makeText(this, "Erreur interne", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }
}