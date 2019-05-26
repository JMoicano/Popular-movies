package br.com.jmoicano.popularmovies.details.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;

public class DetailsActivityViewModel extends ViewModel {

    private MovieRepository mRepository;

    private LiveData<Boolean> favorite;

    private LiveData<MovieModel> mMovie;

    public DetailsActivityViewModel(MovieRepository repository, MovieModel movie) {
        this.mRepository = repository;
        mMovie = new MutableLiveData<>();
        ((MutableLiveData<MovieModel>) mMovie).setValue(movie);
        favorite = mRepository.isFavorite(movie.getId());
    }

    public LiveData<Boolean> getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        ((MutableLiveData<Boolean>) this.favorite).postValue(favorite);
    }

    public LiveData<MovieModel> getMovie() {
        return mMovie;
    }

    public void favorite() {
        mRepository.favoriteMovie(mMovie.getValue());
    }

    public void unfavorite() {
        mRepository.unfavoriteMovie(mMovie.getValue());
    }

}
