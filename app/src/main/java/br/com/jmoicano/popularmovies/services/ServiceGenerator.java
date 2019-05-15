package br.com.jmoicano.popularmovies.services;

import br.com.jmoicano.popularmovies.BuildConfig;
import br.com.jmoicano.popularmovies.services.moviesservices.interceptors.AuthorizationInterceptor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.jmoicano.popularmovies.services.Constants.BASE_URL;

public class ServiceGenerator {

    private ServiceGenerator() {
        throw new IllegalArgumentException("Private constructor");
    }


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .addInterceptor(new AuthorizationInterceptor());

    public static <T> T create(Class<T> serviceClass){
        if(!httpClient.interceptors().contains(logging) && BuildConfig.DEBUG){
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();

        }
        return retrofit.create(serviceClass);
    }

}
