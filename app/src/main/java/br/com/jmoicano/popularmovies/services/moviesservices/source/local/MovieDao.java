package br.com.jmoicano.popularmovies.services.moviesservices.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<MovieModel>> loadAllFavorite();

    @Query("SELECT COUNT (*) FROM movie WHERE id = :id")
    LiveData<Integer> countItem(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void favoriteMovie(MovieModel movie);

    @Delete
    void unfavoriteMovie(MovieModel movie);
}
