package br.com.jmoicano.popularmovies.details.view.adapter;

import br.com.jmoicano.popularmovies.services.moviesmodels.TrailerModel;

public interface TrailerListAdpaterViewModel {
    int numTrailers();
    TrailerModel getTrailerPosition(int pos);
}
