package br.com.jmoicano.popularmovies.details.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.jmoicano.popularmovies.databinding.ReviewItemBinding;
import br.com.jmoicano.popularmovies.services.moviesmodels.ReviewModel;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {
    private ReviewListAdapterViewModel viewModel;

    public ReviewListAdapter(ReviewListAdapterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ReviewItemBinding binding = ReviewItemBinding.inflate(inflater);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewModel item = viewModel.getReviewPosition(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return viewModel.numReviews();
    }

    public void update(){ notifyDataSetChanged(); }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private ReviewItemBinding trailerItemBinding;

        public ReviewViewHolder(ReviewItemBinding binding) {
            super(binding.getRoot());
            trailerItemBinding = binding;
        }

        void bind(ReviewModel item) {
            trailerItemBinding.setItem(item);
        }
    }
}
