package br.com.jmoicano.popularmovies.services.moviesservices.source.remote;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DiscoverService {

    @GET("/3/movie/{sort}")
    Call<MovieDiscoverResponseModel> getMovies(@Path("sort") String sort);
}
