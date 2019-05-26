package br.com.jmoicano.popularmovies.details.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;

public class DetailsActivityViewModelFactory implements ViewModelProvider.Factory {

    private MovieRepository repository;

    private MovieModel movie;

    public DetailsActivityViewModelFactory(MovieRepository repository, MovieModel movie) {
        this.repository = repository;
        this.movie = movie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsActivityViewModel(repository, movie);
    }
}
