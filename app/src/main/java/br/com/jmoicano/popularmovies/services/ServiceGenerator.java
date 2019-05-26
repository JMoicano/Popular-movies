package br.com.jmoicano.popularmovies.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.LocalDate;

import br.com.jmoicano.popularmovies.BuildConfig;
import br.com.jmoicano.popularmovies.services.moviesservices.interceptors.AuthorizationInterceptor;
import br.com.jmoicano.popularmovies.services.moviesservices.source.local.LocalDateConverter;
import br.com.jmoicano.popularmovies.services.moviesservices.source.remote.LocalDateAdapter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.jmoicano.popularmovies.services.Constants.BASE_URL;

public class ServiceGenerator {

    private ServiceGenerator() {
        throw new IllegalArgumentException("Private constructor");
    }


    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));

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
