package br.com.jmoicano.popularmovies.services.moviesservices.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private MovieDataSource mMovieDataSource;

    private MovieRepository(@NonNull MovieDataSource movieDataSource){
        this.mMovieDataSource = movieDataSource;
    }

    public static MovieRepository getInstance(MovieDataSource movieDataSource){
        if(INSTANCE == null){
            synchronized (MovieRepository.class){
                if(INSTANCE == null){
                    INSTANCE = new MovieRepository(movieDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort) {
        return mMovieDataSource.getMovies(sort);
    }
}
