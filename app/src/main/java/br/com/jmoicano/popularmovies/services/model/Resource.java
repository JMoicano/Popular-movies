package br.com.jmoicano.popularmovies.services.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public Status status;
    @Nullable
    public final T data;
    @Nullable
    public final ErrorResponse errorResponse;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable ErrorResponse errorResponse) {
        this.status = status;
        this.data = data;
        this.errorResponse = errorResponse;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(ErrorResponse error){
        return new Resource<>(Status.ERROR, null, error);
    }

    public static <T> Resource<T> loading(){
        return new Resource<>(Status.LOADING, null, null);
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
