package br.com.jmoicano.popularmovies.details.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.jmoicano.popularmovies.databinding.TrailerItemBinding;
import br.com.jmoicano.popularmovies.services.moviesmodels.TrailerModel;

public abstract class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.TrailerViewHolder>
        implements TrailerClickListener {

    private TrailerListAdpaterViewModel viewModel;

    public TrailerListAdapter(TrailerListAdpaterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TrailerItemBinding binding = TrailerItemBinding.inflate(inflater);
        return new TrailerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        TrailerModel item = viewModel.getTrailerPosition(position);
        holder.bind(item);
        holder.trailerItemBinding.setOnClick(this);
    }

    @Override
    public int getItemCount() {
        return viewModel.numTrailers();
    }

    public void update(){ notifyDataSetChanged(); }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        private TrailerItemBinding trailerItemBinding;

        public TrailerViewHolder(TrailerItemBinding binding) {
            super(binding.getRoot());
            trailerItemBinding = binding;
        }

        void bind(TrailerModel item) {
            trailerItemBinding.setItem(item);
        }
    }
}
