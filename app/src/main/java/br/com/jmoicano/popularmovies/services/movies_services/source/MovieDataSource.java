package br.com.jmoicano.popularmovies.services.movies_services.source;

import br.com.jmoicano.popularmovies.services.model.LiveResource;
import br.com.jmoicano.popularmovies.services.movies_models.MovieDiscoverResponseModel;

public interface MovieDataSource {
    LiveResource<MovieDiscoverResponseModel> getMovies(String sort);
}
