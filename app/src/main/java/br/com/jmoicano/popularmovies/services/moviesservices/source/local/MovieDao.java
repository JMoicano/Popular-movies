package br.com.jmoicano.popularmovies.services.moviesservices.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY :orderBy")
    LiveData<List<MovieResultModel>> loadAllFavorite(String orderBy);

    @Insert
    void favoriteMovie(MovieResultModel movie);

    @Delete
    void unfavoriteMovie(MovieResultModel movie);

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<MovieResultModel> loadMovieById(int id);
}
