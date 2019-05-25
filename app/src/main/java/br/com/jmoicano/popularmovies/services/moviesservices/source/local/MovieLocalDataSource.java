package br.com.jmoicano.popularmovies.services.moviesservices.source.local;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieDataSource;

public class MovieLocalDataSource implements MovieDataSource {

    private static volatile MovieLocalDataSource INSTANCE = null;
    private static final Object LOCK = new Object();
    private final MovieDao mMovieDao;

    private MovieLocalDataSource(Context context) {
        this.mMovieDao = AppDatabase.getInstance(context).movieDao();
    }

    public static MovieLocalDataSource getInstance(Context context) {
        if (LOCK == null) {
            synchronized (MovieLocalDataSource.class) {
                INSTANCE = new MovieLocalDataSource(context);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort) {
        final MediatorLiveData<Resource<MovieDiscoverResponseModel>> response
                = new MediatorLiveData<>();
        response.postValue(Resource.<MovieDiscoverResponseModel>loading());
        response.addSource(mMovieDao.loadAllFavorite(sort), new Observer<List<MovieResultModel>>() {
            @Override
            public void onChanged(List<MovieResultModel> movieResultModels) {
                response.postValue(Resource.success(new MovieDiscoverResponseModel(movieResultModels)));
            }
        });
        return response;
    }

    @Override
    public LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort, boolean local) {
        if (local) {
            return getMovies(sort);
        } else {
            return null;
        }
    }
}
