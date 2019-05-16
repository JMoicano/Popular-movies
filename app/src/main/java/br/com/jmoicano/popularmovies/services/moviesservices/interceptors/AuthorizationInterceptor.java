package br.com.jmoicano.popularmovies.services.moviesservices.interceptors;

import java.io.IOException;

import br.com.jmoicano.popularmovies.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static br.com.jmoicano.popularmovies.services.Constants.API_KEY;

public class AuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalUrl = original.url();

        HttpUrl url = originalUrl.newBuilder()
                .addQueryParameter(API_KEY, BuildConfig.MovieDBApi)
                .build();

        Request request = original.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }
}
