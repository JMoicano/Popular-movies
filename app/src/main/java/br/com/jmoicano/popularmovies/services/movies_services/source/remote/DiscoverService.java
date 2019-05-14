package br.com.jmoicano.popularmovies.services.movies_services.source.remote;

import br.com.jmoicano.popularmovies.services.movies_models.MovieDiscoverResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DiscoverService {

    @GET("/discover/movie")
    Call<MovieDiscoverResponseModel> getMovies(@Query("sort_by") String sort);
}
