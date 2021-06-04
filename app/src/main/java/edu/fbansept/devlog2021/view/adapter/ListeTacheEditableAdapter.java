package edu.fbansept.devlog2021.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.databinding.ItemTacheEditableBinding;
import edu.fbansept.devlog2021.model.Tache;

public class ListeTacheEditableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Tache> listeTache;

    private final int VIEW_TYPE_TACHE = 0;
    private final int VIEW_TYPE_BOUTON = 1;

    public ListeTacheEditableAdapter(List<Tache> listeTache) {
        this.listeTache = listeTache;
    }

    static class TacheViewHolder extends RecyclerView.ViewHolder {

        ItemTacheEditableBinding binding;
        ImageButton buttonSupprimerTache;

        public TacheViewHolder(ItemTacheEditableBinding binding) {
            super(binding.getRoot());
            buttonSupprimerTache = binding.getRoot().findViewById(R.id.button_supprimerTache);
            this.binding = binding;
        }

        public void bind(Tache tache) {
            binding.setTache(tache);
            binding.executePendingBindings();
        }
    }

    static class BoutonAjouterTacheViewHolder extends  RecyclerView.ViewHolder {

        Button buttonAjouterTache;

        public BoutonAjouterTacheViewHolder(@NonNull View itemView) {
            super(itemView);
            this.buttonAjouterTache = itemView.findViewById(R.id.button_ajouterTache);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_BOUTON) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_bouton_ajouter_tache,parent, false);

            return new BoutonAjouterTacheViewHolder(view);
        } else {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            ItemTacheEditableBinding binding =
                    ItemTacheEditableBinding.inflate(layoutInflater, parent, false);

            return new TacheViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position) == VIEW_TYPE_BOUTON) {

            BoutonAjouterTacheViewHolder boutonViewHolder = (BoutonAjouterTacheViewHolder)holder;
            boutonViewHolder.buttonAjouterTache.setOnClickListener(v -> {
                listeTache.add(new Tache());
                //ne met à jour que le dernier élément du recycler view (+ performant)
                notifyItemInserted(listeTache.size() - 1);
            });

        } else {

            TacheViewHolder tacheViewHolder = (TacheViewHolder)holder;
            tacheViewHolder.bind(listeTache.get(position));

            tacheViewHolder.buttonSupprimerTache.setOnClickListener(v -> {
                listeTache.remove(position);
                //met à jour tout le recycler view
                notifyDataSetChanged();
            });

        }

    }

    @Override
    public int getItemCount() {
        return listeTache.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listeTache.size()) ? VIEW_TYPE_BOUTON : VIEW_TYPE_TACHE;
    }
}
