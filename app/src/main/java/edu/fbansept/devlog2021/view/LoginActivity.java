package edu.fbansept.devlog2021.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.controller.ConnexionController;
import edu.fbansept.devlog2021.controller.NoteController;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextPseudo;
    private EditText editTextPassword;
    private Button buttonConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        editTextPseudo = findViewById(R.id.editText_pseudo);
        editTextPassword = findViewById(R.id.editText_password);
        buttonConnexion = findViewById(R.id.button_connexion);
        init();
    }

    private void init() {

        this.buttonConnexion.setOnClickListener((View v) -> {
            ConnexionController.getInstance().connexion(
                    this,
                    editTextPseudo.getText().toString(),
                    editTextPassword.getText().toString(),
                    () -> {
                        startActivity(new Intent(this,ListeNoteActivity.class));
                    }
            );
        });
    }
}