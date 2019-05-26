package br.com.jmoicano.popularmovies.services.moviesservices.source;

import androidx.lifecycle.LiveData;

import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MoviesListModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.ReviewsListModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.TrailersListModel;

public interface MovieDataSource {
    LiveData<Resource<MoviesListModel>> getMovies(String sort);

    LiveData<Resource<TrailersListModel>> getTrailers(String movieId);

    LiveData<Resource<ReviewsListModel>> getReviews(String movieId);

    void favoriteMovie(MovieModel movie);

    void unfavoriteMovie(MovieModel movie);

    LiveData<Boolean> isFavorite(int id);
}
