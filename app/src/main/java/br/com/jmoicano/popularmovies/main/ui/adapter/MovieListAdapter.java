package br.com.jmoicano.popularmovies.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import br.com.jmoicano.popularmovies.databinding.MovieItemBinding;
import br.com.jmoicano.popularmovies.services.movies_models.MovieResultModel;

import static br.com.jmoicano.popularmovies.services.Constants.IMAGE_URL;

public abstract class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>
        implements MovieClickListener {

    private MovieListAdapterViewModel mViewModel;

    public MovieListAdapter(MovieListAdapterViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieItemBinding binding = MovieItemBinding.inflate(inflater);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieResultModel item = mViewModel.getPosition(position);
        holder.bind(item);
        holder.mMovieItemBinding.setOnClick(this);

        Picasso.get()
                .load(IMAGE_URL + item.getPosterPath())
//                .placeholder( TODO: Add loading image)
//                .error(TODO: Add error image)
                .into(holder.mMovieItemBinding.ivPosterThumb);
    }

    @Override
    public int getItemCount() {
        return mViewModel.numMovies();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding mMovieItemBinding;

        MovieViewHolder(MovieItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mMovieItemBinding = itemBinding;
        }

        void bind(MovieResultModel item) {
            mMovieItemBinding.setItem(item);
        }
    }
}
