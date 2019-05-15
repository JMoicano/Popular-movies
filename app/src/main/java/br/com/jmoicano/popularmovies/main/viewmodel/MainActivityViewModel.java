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
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;
import br.com.jmoicano.popularmovies.services.moviesservices.source.remote.MovieRemoteDataSource;

import static br.com.jmoicano.popularmovies.services.Constants.POPULARITY;

public class MainActivityViewModel extends ViewModel implements MovieListAdapterViewModel {

    private List<MovieResultModel> mMovies;

    private MovieRepository repository;

    private LiveData<Resource<MovieDiscoverResponseModel>> discoverData;

    private LiveData<String> mSort;

    public MainActivityViewModel() {
        repository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        mMovies = new ArrayList<>();
        discoverData = new LiveResource<>();
        mSort = new MutableLiveData<>();
        discoverData = Transformations.switchMap(mSort, new Function<String, LiveData<Resource<MovieDiscoverResponseModel>>>() {
            @Override
            public LiveData<Resource<MovieDiscoverResponseModel>> apply(String input) {
                return getMovies(input);
            }
        });
        getMovies(POPULARITY);
    }

    public LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort) {
        return repository.getMovies(sort);
    }

    public void updateMovies(List<MovieResultModel> movies) {
        mMovies = movies;
    }

    public LiveData<Resource<MovieDiscoverResponseModel>> getDiscoverData() {
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
    public MovieResultModel getPosition(int pos) {
        return mMovies.get(pos);
    }
}
