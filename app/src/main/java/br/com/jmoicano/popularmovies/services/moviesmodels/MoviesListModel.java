package br.com.jmoicano.popularmovies.services.moviesmodels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesListModel implements Parcelable {

    @Expose
    @SerializedName("results")
    private List<MovieModel> results;

    public MoviesListModel(List<MovieModel> results) {
        this.results = results;
    }

    protected MoviesListModel(Parcel in) {
        results = in.createTypedArrayList(MovieModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MoviesListModel> CREATOR = new Creator<MoviesListModel>() {
        @Override
        public MoviesListModel createFromParcel(Parcel in) {
            return new MoviesListModel(in);
        }

        @Override
        public MoviesListModel[] newArray(int size) {
            return new MoviesListModel[size];
        }
    };

    public List<MovieModel> getResults() {
        return results;
    }

    public void setResults(List<MovieModel> results) {
        this.results = results;
    }


}
