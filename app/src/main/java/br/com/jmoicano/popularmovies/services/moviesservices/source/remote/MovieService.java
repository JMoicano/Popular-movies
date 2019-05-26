package br.com.jmoicano.popularmovies.services.moviesservices.source.remote;

import br.com.jmoicano.popularmovies.services.moviesmodels.MoviesListModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.ReviewsListModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.TrailersListModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

    @GET("/3/movie/{sort}")
    Call<MoviesListModel> getMovies(@Path("sort") String sort);

    @GET("3/movie/{id}/videos")
    Call<TrailersListModel> getTrailers(@Path("id") String movieId);

    @GET("3/movie/{id}/reviews")
    Call<ReviewsListModel> getReviews(@Path("id") String movieId);
}
