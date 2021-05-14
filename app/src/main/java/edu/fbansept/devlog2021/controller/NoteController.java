package edu.fbansept.devlog2021.controller;

import android.util.Log;

import edu.fbansept.devlog2021.view.LoginActivity;

public final class NoteController {

    private static NoteController instance = null;

    private NoteController() {
    }

    public static NoteController getInstance() {

        if(instance == null) {
            instance = new NoteController();
        }

        return instance;
    }
}
