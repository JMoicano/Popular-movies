package br.com.jmoicano.popularmovies.services.moviesmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersListModel implements Parcelable {

    @SerializedName("results")
    @Expose
    private List<TrailerModel> results;

    public TrailersListModel(List<TrailerModel> results) {
        this.results = results;
    }

    protected TrailersListModel(Parcel in) {
        results = in.createTypedArrayList(TrailerModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrailersListModel> CREATOR = new Creator<TrailersListModel>() {
        @Override
        public TrailersListModel createFromParcel(Parcel in) {
            return new TrailersListModel(in);
        }

        @Override
        public TrailersListModel[] newArray(int size) {
            return new TrailersListModel[size];
        }
    };

    public List<TrailerModel> getResults() {
        return results;
    }

    public void setResults(List<TrailerModel> results) {
        this.results = results;
    }
}
