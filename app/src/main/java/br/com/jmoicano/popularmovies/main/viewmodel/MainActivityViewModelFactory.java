package br.com.jmoicano.popularmovies.main.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private MovieRepository mRepository;

    public MainActivityViewModelFactory(MovieRepository mRepository) {
        this.mRepository = mRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(mRepository);
    }
}
