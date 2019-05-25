package br.com.jmoicano.popularmovies.services.moviesservices.source;

import androidx.lifecycle.LiveData;

import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;

public interface MovieDataSource {
    LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort);

    LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort, boolean local);
}
