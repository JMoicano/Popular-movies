package br.com.jmoicano.popularmovies.details.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;

public class DetailsActivityViewModelFactory implements ViewModelProvider.Factory {

    private MovieRepository repository;

    private MovieResultModel movie;

    public DetailsActivityViewModelFactory(MovieRepository repository, MovieResultModel movie) {
        this.repository = repository;
        this.movie = movie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsActivityViewModel(repository, movie);
    }
}
