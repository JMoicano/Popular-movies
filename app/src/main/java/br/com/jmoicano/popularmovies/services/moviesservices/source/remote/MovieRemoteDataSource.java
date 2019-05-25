package br.com.jmoicano.popularmovies.services.moviesservices.source.remote;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.jmoicano.popularmovies.services.ServiceGenerator;
import br.com.jmoicano.popularmovies.services.model.ErrorResponse;
import br.com.jmoicano.popularmovies.services.model.LiveResource;
import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.jmoicano.popularmovies.services.Constants.CONNECTION_MSG_ERROR;
import static br.com.jmoicano.popularmovies.services.Constants.GENERIC_MSG_ERROR;
import static br.com.jmoicano.popularmovies.services.Constants.SRV_MSG_ERROR;


public class MovieRemoteDataSource implements MovieDataSource {

    private volatile static MovieRemoteDataSource INSTANCE = null;
    private static final Object LOCK = new Object();
    private DiscoverService mDiscoverService;

    private MovieRemoteDataSource() {
        mDiscoverService = ServiceGenerator.create(DiscoverService.class);
    }

    public static MovieRemoteDataSource getInstance() {
        if (INSTANCE == null){
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRemoteDataSource();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort) {
        Call<MovieDiscoverResponseModel> call = mDiscoverService.getMovies(sort);

        final LiveResource<MovieDiscoverResponseModel> movies = new LiveResource<>();
        movies.setValue(Resource.<MovieDiscoverResponseModel>loading());

        call.enqueue(new Callback<MovieDiscoverResponseModel>() {
            @Override
            public void onResponse(Call<MovieDiscoverResponseModel> call, Response<MovieDiscoverResponseModel> response) {
                if(response.body() != null){
                    if(response.isSuccessful()){
                        movies.setValue(Resource.success(response.body()));
                    } else {
                        if(response.errorBody() != null){
                            ErrorResponse error = null;
                            if (response.code() >= 400 && response.code() < 500) {
                                try {
                                    error = (ErrorResponse) new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                error = new ErrorResponse(SRV_MSG_ERROR,
                                        response.code());
                            }
                            movies.setValue(Resource.<MovieDiscoverResponseModel>error(error));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDiscoverResponseModel> call, Throwable t) {
                ErrorResponse error;
                if (t instanceof IOException) {
                    error = new ErrorResponse(CONNECTION_MSG_ERROR,
                            503);
                } else {
                    error = new ErrorResponse(GENERIC_MSG_ERROR,
                            500);
                }
                movies.setValue(Resource.<MovieDiscoverResponseModel>error(error));

            }
        });
        return movies;
    }

    @Override
    public LiveData<Resource<MovieDiscoverResponseModel>> getMovies(String sort, boolean local) {
        if (local) {
            return null;
        } else {
            return getMovies(sort);
        }
    }
}
