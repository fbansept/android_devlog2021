package edu.fbansept.devlog2021.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.model.Tache;

public class ListeTacheAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Tache> listeTache;

    public ListeTacheAdapter(List<Tache> listeTache) {
        this.listeTache = listeTache;
    }

    static class TacheViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBoxTermine;

        public TacheViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTermine = itemView.findViewById(R.id.checkBox_termine);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_tache, parent, false);

        return new TacheViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TacheViewHolder tacheViewHolder = (TacheViewHolder)holder;
        Tache tache = listeTache.get(position);
        tacheViewHolder.checkBoxTermine.setText(tache.getTexte());
        tacheViewHolder.checkBoxTermine.setChecked(tache.isTermine());
        tacheViewHolder.checkBoxTermine.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return listeTache.size();
    }
}
