package br.com.jmoicano.popularmovies.main.viewModel;

import java.util.List;

import br.com.jmoicano.popularmovies.main.view.adapter.MovieListAdapterViewModel;
import br.com.jmoicano.popularmovies.services.movies_models.MovieResultModel;
import br.com.jmoicano.popularmovies.services.movies_services.source.MovieRepository;

public class MainActivityViewModel implements MovieListAdapterViewModel {

    private List<MovieResultModel> mMovies;

    private MovieRepository repository;

    //TODO: initialize list requesting from repository

    @Override
    public int numMovies() {
        return mMovies.size();
    }

    @Override
    public MovieResultModel getPosition(int pos) {
        return mMovies.get(pos);
    }
}
