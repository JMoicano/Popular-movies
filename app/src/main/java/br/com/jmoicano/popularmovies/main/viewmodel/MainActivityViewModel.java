package br.com.jmoicano.popularmovies.main.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.jmoicano.popularmovies.main.view.adapter.MovieListAdapterViewModel;
import br.com.jmoicano.popularmovies.services.model.LiveResource;
import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MoviesListModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;

import static br.com.jmoicano.popularmovies.services.Constants.POPULARITY;

public class MainActivityViewModel extends ViewModel implements MovieListAdapterViewModel {

    private List<MovieModel> mMovies;

    private MovieRepository mRepository;

    private LiveData<Resource<MoviesListModel>> discoverData;

    private LiveData<String> mSort;

    public MainActivityViewModel(MovieRepository repository) {
        mRepository = repository;
        mMovies = new ArrayList<>();
        discoverData = new LiveResource<>();
        mSort = new MutableLiveData<>();
        discoverData = Transformations.switchMap(mSort, new Function<String, LiveData<Resource<MoviesListModel>>>() {
            @Override
            public LiveData<Resource<MoviesListModel>> apply(String input) {
                return getMovies(input);
            }
        });
        setSort(POPULARITY);
    }

    public LiveData<Resource<MoviesListModel>> getMovies(String sort) {
        return mRepository.getMovies(sort);
    }

    public void updateMovies(List<MovieModel> movies) {
        mMovies = movies;
    }

    public LiveData<Resource<MoviesListModel>> getDiscoverData() {
        return discoverData;
    }

    public void setSort(String sort){
        ((MutableLiveData<String>) mSort).postValue(sort);
    }

    public LiveData<String> getSort() {
        return mSort;
    }

    @Override
    public int numMovies() {
        return mMovies.size();
    }

    @Override
    public MovieModel getPosition(int pos) {
        return mMovies.get(pos);
    }
}
