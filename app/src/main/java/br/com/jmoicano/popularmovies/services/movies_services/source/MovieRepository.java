package br.com.jmoicano.popularmovies.services.movies_services.source;

import androidx.annotation.NonNull;

import br.com.jmoicano.popularmovies.services.model.LiveResource;
import br.com.jmoicano.popularmovies.services.movies_models.MovieDiscoverResponseModel;

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
    public LiveResource<MovieDiscoverResponseModel> getMovies(String sort) {
        return mMovieDataSource.getMovies(sort);
    }
}
