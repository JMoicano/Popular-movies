package br.com.jmoicano.popularmovies.services.moviesservices.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;

import static br.com.jmoicano.popularmovies.services.Constants.FAVORITE;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private MovieDataSource mMovieRemoteDataSource;

    private MovieDataSource mMovieLocalDataSource;

    private MovieRepository(@NonNull MovieDataSource movieRemoteDataSource, @NonNull MovieDataSource movieLocalDataSource){
        this.mMovieRemoteDataSource = movieRemoteDataSource;
        this.mMovieLocalDataSource = movieLocalDataSource;
    }

    public static MovieRepository getInstance(MovieDataSource movieRemoteDataSource, MovieDataSource movieLocalDataSource){
        if(INSTANCE == null){
            synchronized (MovieRepository.class){
                if(INSTANCE == null){
                    INSTANCE = new MovieRepository(movieRemoteDataSource, movieLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort) {
        if (sort.equals(FAVORITE)){
            return mMovieLocalDataSource.getMovies(sort);
        } else {
            return mMovieRemoteDataSource.getMovies(sort);
        }
    }

    @Override
    public void favoriteMovie(MovieResultModel movie) {
        mMovieLocalDataSource.favoriteMovie(movie);
    }

    @Override
    public void unfavoriteMovie(MovieResultModel movie) {
        mMovieLocalDataSource.unfavoriteMovie(movie);
    }

    @Override
    public LiveData<Boolean> isFavorite(int id) {
        return mMovieLocalDataSource.isFavorite(id);
    }
}
