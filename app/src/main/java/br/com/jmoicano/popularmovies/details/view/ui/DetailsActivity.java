package br.com.jmoicano.popularmovies.details.view.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.CompoundButton;

import java.io.Serializable;

import br.com.jmoicano.popularmovies.R;
import br.com.jmoicano.popularmovies.databinding.ActivityDetailsBinding;
import br.com.jmoicano.popularmovies.details.viewmodel.DetailsActivityViewModel;
import br.com.jmoicano.popularmovies.details.viewmodel.DetailsActivityViewModelFactory;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;
import br.com.jmoicano.popularmovies.services.moviesservices.source.local.MovieLocalDataSource;
import br.com.jmoicano.popularmovies.services.moviesservices.source.remote.MovieRemoteDataSource;

import static br.com.jmoicano.popularmovies.services.Constants.MOVIE_EXTRA;

public class DetailsActivity extends AppCompatActivity {

    private MovieResultModel movie;

    private DetailsActivityViewModel viewModel;

    private ActivityDetailsBinding binding;

    private void setupViewModel() {
        if (getIntent().hasExtra(MOVIE_EXTRA)) {
            movie = (MovieResultModel) getIntent().getSerializableExtra(MOVIE_EXTRA);
            viewModel = ViewModelProviders.of(
                    this,
                    new DetailsActivityViewModelFactory(
                            MovieRepository.getInstance(
                                    MovieRemoteDataSource.getInstance(),
                                    MovieLocalDataSource.getInstance(
                                            this
                                    )
                            ),
                            movie
                    )
            ).get(DetailsActivityViewModel.class);
        }
    }

    private void setupObserver() {
        Observer<Boolean> favoriteObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.favoriteButton.setChecked(aBoolean);
                if (aBoolean) {
                    viewModel.favorite();
                } else {
                    viewModel.unfavorite();
                }
            }
        };
        viewModel.getFavorite().observe(this, favoriteObserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        setupObserver();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setFavorite(isChecked);
            }
        });
    }

}
