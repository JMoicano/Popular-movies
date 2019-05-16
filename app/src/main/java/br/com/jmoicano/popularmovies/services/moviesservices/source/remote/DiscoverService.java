package br.com.jmoicano.popularmovies.services.moviesservices.source.remote;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DiscoverService {

    @GET("/3/discover/movie")
    Call<MovieDiscoverResponseModel> getMovies(@Query("sort_by") String sort);
}
