package br.com.jmoicano.popularmovies.services.movies_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieResultModel implements Serializable {

    @Expose
    @SerializedName("poster_path")
    private String posterPath;

    @Expose
    @SerializedName("adult")
    private boolean adult;

    @Expose
    @SerializedName("release_date")
    private String releaseDate;

    @Expose
    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("original_title")
    private String originalTitle;

    @Expose
    @SerializedName("original_language")
    private String originalLenguage;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("backdrop_path")
    private String backdropPath;

    @Expose
    @SerializedName("popularity")
    private double popularity;

    @Expose
    @SerializedName("vote_count")
    private int voteCount;

    @Expose
    @SerializedName("video")
    private boolean video;

    @Expose
    @SerializedName("vote_average")
    private double voteAverage;

    public MovieResultModel(String posterPath, boolean adult, String releaseDate, List<Integer> genreIds, int id, String originalTitle, String originalLenguage, String title, String backdropPath, double popularity, int voteCount, boolean video, double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLenguage = originalLenguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLenguage() {
        return originalLenguage;
    }

    public void setOriginalLenguage(String originalLenguage) {
        this.originalLenguage = originalLenguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
