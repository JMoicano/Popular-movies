package br.com.jmoicano.popularmovies.details.view.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.view.View;

import java.io.Serializable;

import br.com.jmoicano.popularmovies.R;
import br.com.jmoicano.popularmovies.databinding.ActivityDetailsBinding;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;

import static br.com.jmoicano.popularmovies.services.Constants.MOVIE_EXTRA;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra(MOVIE_EXTRA)) {
            ActivityDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
            MovieResultModel movie = (MovieResultModel) getIntent().getSerializableExtra(MOVIE_EXTRA);
            binding.setMovie(movie);
            binding.setLifecycleOwner(this);
        }
    }

}
