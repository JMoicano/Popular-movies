package br.com.jmoicano.popularmovies.services;

import br.com.jmoicano.popularmovies.services.movies_services.interceptors.AuthorizationInterceptor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.jmoicano.popularmovies.services.Constants.BASE_URL;
import static br.com.jmoicano.popularmovies.services.Constants.SCHEME;
import static br.com.jmoicano.popularmovies.services.Constants.VERSION;

public class ServiceGenerator {

    private static HttpUrl buildUrl(){
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(BASE_URL)
                .addPathSegment(VERSION)
                .build();
    }

    private static OkHttpClient buildHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationInterceptor())
                .build();
    }

    public static <T> T create(Class<T> serviceClass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(buildUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }

}
