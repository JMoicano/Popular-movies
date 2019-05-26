package br.com.jmoicano.popularmovies.details.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.jmoicano.popularmovies.details.view.adapter.ReviewListAdapterViewModel;
import br.com.jmoicano.popularmovies.details.view.adapter.TrailerListAdpaterViewModel;
import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.ReviewModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.ReviewsListModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.TrailerModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.TrailersListModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;

public class DetailsActivityViewModel extends ViewModel
        implements TrailerListAdpaterViewModel, ReviewListAdapterViewModel {

    List<TrailerModel> mTrailers;

    List<ReviewModel> mReviews;

    private MovieRepository mRepository;

    private LiveData<Boolean> favorite;

    private LiveData<MovieModel> mMovie;

    private LiveData<Resource<TrailersListModel>> mTrailerList;

    private LiveData<Resource<ReviewsListModel>> mReviewList;

    public DetailsActivityViewModel(MovieRepository repository, MovieModel movie) {
        this.mRepository = repository;
        mMovie = new MutableLiveData<>();
        ((MutableLiveData<MovieModel>) mMovie).setValue(movie);
        favorite = mRepository.isFavorite(movie.getId());
        mTrailerList = mRepository.getTrailers(movie.getId());
        mReviewList = mRepository.getReviews(movie.getId());
    }

    public LiveData<Boolean> getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        ((MutableLiveData<Boolean>) this.favorite).postValue(favorite);
    }

    public LiveData<MovieModel> getMovie() {
        return mMovie;
    }

    public LiveData<Resource<TrailersListModel>> getTrailerList() {
        return mTrailerList;
    }

    public LiveData<Resource<ReviewsListModel>> getReviewList() {
        return mReviewList;
    }

    public void updateReview (List<ReviewModel> reviews) {
        mReviews = reviews;
    }

    public void updateTrailer (List<TrailerModel> trailers) {
        mTrailers = trailers;
    }

    public void favorite() {
        mRepository.favoriteMovie(mMovie.getValue());
    }

    public void unfavorite() {
        mRepository.unfavoriteMovie(mMovie.getValue());
    }

    @Override
    public int numTrailers() {
        return mTrailers.size();
    }

    @Override
    public int numReviews() {
        return mReviews.size();
    }

    @Override
    public ReviewModel getReviewPosition(int pos) {
        return mReviews.get(pos);
    }

    @Override
    public TrailerModel getTrailerPosition(int pos) {
        return mTrailers.get(pos);
    }
}
