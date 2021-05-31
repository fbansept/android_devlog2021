package edu.fbansept.devlog2021.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.model.Note;
import edu.fbansept.devlog2021.model.NoteListe;
import edu.fbansept.devlog2021.model.NoteTexte;

public class ListeNoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Note> listeNote;
    private ClickNoteListener listener;

    private final int VIEWTYPE_NOTE_TEXTE = 1;
    private final int VIEWTYPE_NOTE_LISTE = 2;

    public interface ClickNoteListener {
        void onClickNoteListener(Note note);
    }

    public ListeNoteAdapter(List<Note> listeNote, ClickNoteListener listener) {
        this.listeNote = listeNote;
        this.listener = listener;
    }

    static class NoteTexteViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitreNote;
        TextView textViewContenu;
        LinearLayout layout;

        public NoteTexteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitreNote = itemView.findViewById(R.id.textView_titreNoteTexte);
            textViewContenu = itemView.findViewById(R.id.textView_contenuNote);
            layout = itemView.findViewById(R.id.linearLayout_noteTexte);
        }
    }

    static class NoteListeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitreNote;
        RecyclerView recyclerViewListeTache;
        LinearLayout layout;

        public NoteListeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitreNote = itemView.findViewById(R.id.textView_titreNoteListe);
            recyclerViewListeTache = itemView.findViewById(R.id.recyclerView_listeTache);
            recyclerViewListeTache.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            layout = itemView.findViewById(R.id.linearLayout_noteListe);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //si l'item courant est de type NoteTexte
        if(viewType == VIEWTYPE_NOTE_TEXTE) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_note_texte, parent, false);

            return new NoteTexteViewHolder(view);
        //si l'item courant est de type NoteListe
        } else {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_note_liste, parent, false);

            return new NoteListeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder.getItemViewType() == VIEWTYPE_NOTE_TEXTE) {

            NoteTexte note = (NoteTexte) listeNote.get(position);
            NoteTexteViewHolder noteTexteViewHolder = (NoteTexteViewHolder) holder;
            noteTexteViewHolder.textViewTitreNote.setText(note.getTitre());
            noteTexteViewHolder.textViewContenu.setText(note.getTexte());

            noteTexteViewHolder.layout.setOnClickListener(v -> {
                listener.onClickNoteListener(note);
            });

        } else {
            NoteListe note = (NoteListe) listeNote.get(position);
            NoteListeViewHolder noteListeViewHolder = (NoteListeViewHolder) holder;
            noteListeViewHolder.textViewTitreNote.setText(note.getTitre());
            noteListeViewHolder.recyclerViewListeTache.setAdapter(
                    new ListeTacheAdapter(note.getListeTache())
            );

            noteListeViewHolder.layout.setOnClickListener(v -> {
                listener.onClickNoteListener(note);
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return listeNote.get(position) instanceof NoteTexte ? VIEWTYPE_NOTE_TEXTE : VIEWTYPE_NOTE_LISTE;
    }

    @Override
    public int getItemCount() {
        return listeNote.size();
    }


}
