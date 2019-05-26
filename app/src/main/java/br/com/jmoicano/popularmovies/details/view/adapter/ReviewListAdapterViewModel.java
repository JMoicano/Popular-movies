package br.com.jmoicano.popularmovies.details.view.adapter;

import br.com.jmoicano.popularmovies.services.moviesmodels.ReviewModel;

public interface ReviewListAdapterViewModel {
    int numReviews();
    ReviewModel getReviewPosition(int pos);
}
