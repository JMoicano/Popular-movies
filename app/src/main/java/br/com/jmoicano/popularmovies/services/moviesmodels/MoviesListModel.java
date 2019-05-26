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

    public List<MovieModel> getResults() {
        return results;
    }

    public void setResults(List<MovieModel> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
    }

    @Ignore
    public MoviesListModel(Parcel in) {
        in.readList(results, MovieModel.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MoviesListModel createFromParcel(Parcel in) {
            return new MoviesListModel(in);
        }

        public MoviesListModel[] newArray(int size) {
            return new MoviesListModel[size];
        }
    };

}
