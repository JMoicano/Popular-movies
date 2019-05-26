package br.com.jmoicano.popularmovies.services.moviesservices.source;

import androidx.lifecycle.LiveData;

import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;

public interface MovieDataSource {
    LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort);

    void favoriteMovie(MovieResultModel movie);

    void unfavoriteMovie(MovieResultModel movie);

    LiveData<Boolean> isFavorite(int id);
}
