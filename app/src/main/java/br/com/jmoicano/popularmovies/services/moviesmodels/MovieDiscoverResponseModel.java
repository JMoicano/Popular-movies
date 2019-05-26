package br.com.jmoicano.popularmovies.services.moviesmodels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieDiscoverResponseModel implements Parcelable {

    @Expose
    @SerializedName("results")
    private List<MovieResultModel> results;

    public MovieDiscoverResponseModel(List<MovieResultModel> results) {
        this.results = results;
    }

    public List<MovieResultModel> getResults() {
        return results;
    }

    public void setResults(List<MovieResultModel> results) {
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
    public MovieDiscoverResponseModel(Parcel in) {
        in.readList(results, MovieResultModel.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieDiscoverResponseModel createFromParcel(Parcel in) {
            return new MovieDiscoverResponseModel(in);
        }

        public MovieDiscoverResponseModel[] newArray(int size) {
            return new MovieDiscoverResponseModel[size];
        }
    };

}
