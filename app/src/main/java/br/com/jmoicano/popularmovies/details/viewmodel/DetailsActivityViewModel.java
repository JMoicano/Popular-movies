package br.com.jmoicano.popularmovies.details.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;

public class DetailsActivityViewModel extends ViewModel {

    private MovieRepository mRepository;

    private LiveData<Boolean> favorite;

    private LiveData<MovieResultModel> mMovie;

    public DetailsActivityViewModel(MovieRepository repository, MovieResultModel movie) {
        this.mRepository = repository;
        mMovie = new MutableLiveData<>();
        ((MutableLiveData<MovieResultModel>) mMovie).setValue(movie);
        favorite = mRepository.isFavorite(movie.getId());
    }

    public LiveData<Boolean> getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        ((MutableLiveData<Boolean>) this.favorite).postValue(favorite);
    }

    public LiveData<MovieResultModel> getMovie() {
        return mMovie;
    }

    public void favorite() {
        mRepository.favoriteMovie(mMovie.getValue());
    }

    public void unfavorite() {
        mRepository.unfavoriteMovie(mMovie.getValue());
    }

}
