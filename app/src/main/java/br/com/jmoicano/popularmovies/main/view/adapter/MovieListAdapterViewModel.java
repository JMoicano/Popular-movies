package br.com.jmoicano.popularmovies.main.view.adapter;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;

public interface MovieListAdapterViewModel {
    int numMovies();
    MovieModel getPosition(int pos);
}
