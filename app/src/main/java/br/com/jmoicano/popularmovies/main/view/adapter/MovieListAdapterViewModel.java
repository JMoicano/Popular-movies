package br.com.jmoicano.popularmovies.main.view.adapter;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;

public interface MovieListAdapterViewModel {
    int numMovies();
    MovieResultModel getPosition(int pos);
}
