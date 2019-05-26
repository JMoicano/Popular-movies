package br.com.jmoicano.popularmovies.main.view.adapter;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;

public interface MovieClickListener {
    void onMovieClick(MovieModel movie);
}
