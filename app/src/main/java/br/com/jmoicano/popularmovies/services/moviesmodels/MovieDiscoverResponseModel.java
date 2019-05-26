package br.com.jmoicano.popularmovies.services.moviesmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieDiscoverResponseModel implements Serializable {

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

}
