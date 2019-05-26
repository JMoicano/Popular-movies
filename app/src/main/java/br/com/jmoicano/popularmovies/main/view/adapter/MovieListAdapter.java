package br.com.jmoicano.popularmovies.main.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import br.com.jmoicano.popularmovies.R;
import br.com.jmoicano.popularmovies.databinding.MovieItemBinding;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;

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
        MovieModel item = mViewModel.getPosition(position);
        holder.bind(item);
        holder.mMovieItemBinding.setOnClick(this);
    }

    @Override
    public int getItemCount() {
        return mViewModel.numMovies();
    }

    public void update(){ notifyDataSetChanged(); }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding mMovieItemBinding;

        MovieViewHolder(MovieItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mMovieItemBinding = itemBinding;
        }

        void bind(MovieModel item) {
            mMovieItemBinding.setItem(item);
        }
    }
}
