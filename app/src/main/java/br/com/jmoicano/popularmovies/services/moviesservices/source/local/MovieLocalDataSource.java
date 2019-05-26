package br.com.jmoicano.popularmovies.services.moviesservices.source.local;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MoviesListModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieDataSource;

public class MovieLocalDataSource implements MovieDataSource {

    private static volatile MovieLocalDataSource INSTANCE = null;
    private static final Object LOCK = new Object();
    private final MovieDao mMovieDao;

    private MovieLocalDataSource(Context context) {
        this.mMovieDao = AppDatabase.getInstance(context).movieDao();
    }

    public static MovieLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new MovieLocalDataSource(context);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<MoviesListModel>> getMovies(final String sort) {
        final MediatorLiveData<Resource<MoviesListModel>> response
                = new MediatorLiveData<>();
        response.postValue(Resource.<MoviesListModel>loading());

        response.addSource(mMovieDao.loadAllFavorite(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                response.postValue(Resource.success(new MoviesListModel(movieModels)));
            }
        });
        return response;
    }

    @Override
    public void favoriteMovie(final MovieModel movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.favoriteMovie(movie);
            }
        });
    }

    @Override
    public void unfavoriteMovie(final MovieModel movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.unfavoriteMovie(movie);
            }
        });
    }

    @Override
    public LiveData<Boolean> isFavorite(final int id) {
        final MediatorLiveData<Boolean> isFavorite = new MediatorLiveData<>();

        isFavorite.addSource(mMovieDao.countItem(id), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                isFavorite.postValue(integer != 0);
            }
        });
        return isFavorite;
    }
}
