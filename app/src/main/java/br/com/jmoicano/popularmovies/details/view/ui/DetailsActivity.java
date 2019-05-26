package br.com.jmoicano.popularmovies.details.view.ui;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.jmoicano.popularmovies.R;
import br.com.jmoicano.popularmovies.databinding.ActivityDetailsBinding;
import br.com.jmoicano.popularmovies.details.view.adapter.ReviewListAdapter;
import br.com.jmoicano.popularmovies.details.view.adapter.TrailerListAdapter;
import br.com.jmoicano.popularmovies.details.viewmodel.DetailsActivityViewModel;
import br.com.jmoicano.popularmovies.details.viewmodel.DetailsActivityViewModelFactory;
import br.com.jmoicano.popularmovies.services.model.ErrorResponse;
import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.ReviewsListModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.TrailerModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.TrailersListModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;
import br.com.jmoicano.popularmovies.services.moviesservices.source.local.MovieLocalDataSource;
import br.com.jmoicano.popularmovies.services.moviesservices.source.remote.MovieRemoteDataSource;

import static br.com.jmoicano.popularmovies.services.Constants.MOVIE_EXTRA;
import static br.com.jmoicano.popularmovies.services.Constants.YOUTUBE_URI;
import static br.com.jmoicano.popularmovies.services.Constants.YOUTUBE_URL;

public class DetailsActivity extends AppCompatActivity {

    private MovieModel movie;

    private DetailsActivityViewModel viewModel;

    private ActivityDetailsBinding binding;

    private TrailerListAdapter trailerAdapter;

    private ReviewListAdapter reviewAdapter;

    private void setupViewModel() {
        if (getIntent().hasExtra(MOVIE_EXTRA)) {
            movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
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
        Observer<Resource<ReviewsListModel>> reviewObserver = new Observer<Resource<ReviewsListModel>>() {
            @Override
            public void onChanged(Resource<ReviewsListModel> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        if (resource.data != null) {
                            viewModel.updateReview(resource.data.getResults());
                            reviewAdapter.update();
                        }
                        break;
                    case ERROR:
                        if (resource.errorResponse != null) {
                            ErrorResponse errorResponse = resource.errorResponse;
                            setError(errorResponse);
                        }
                        break;
                    case LOADING:
                        break;
                }
            }
        };
        Observer<Resource<TrailersListModel>> trailerObserver = new Observer<Resource<TrailersListModel>>() {
            @Override
            public void onChanged(Resource<TrailersListModel> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        if (resource.data != null) {
                            viewModel.updateTrailer(resource.data.getResults());
                            trailerAdapter.update();
                        }
                        break;
                    case ERROR:
                        if (resource.errorResponse != null) {
                            ErrorResponse errorResponse = resource.errorResponse;
                            setError(errorResponse);
                        }
                        break;
                    case LOADING:
                        break;
                }
            }
        };
        viewModel.getFavorite().observe(this, favoriteObserver);
        viewModel.getReviewList().observe(this, reviewObserver);
        viewModel.getTrailerList().observe(this, trailerObserver);
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

        binding.tvLabelReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.rvReviews.getVisibility() == View.GONE) {
                    binding.rvReviews.setVisibility(View.VISIBLE);
                } else {
                    binding.rvReviews.setVisibility(View.GONE);
                }
            }
        });

        binding.tvLabelTrailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.rvTrailers.getVisibility() == View.GONE) {
                    binding.rvTrailers.setVisibility(View.VISIBLE);
                } else {
                    binding.rvTrailers.setVisibility(View.GONE);
                }
            }
        });

        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvReviews.setLayoutManager(reviewLayoutManager);
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvTrailers.setLayoutManager(trailerLayoutManager);

        trailerAdapter = new TrailerListAdapter(viewModel) {
            @Override
            public void onTrailerClick(TrailerModel trailer) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_URI + trailer.getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(YOUTUBE_URL + trailer.getKey()));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        };

        binding.rvTrailers.setAdapter(trailerAdapter);

        reviewAdapter = new ReviewListAdapter(viewModel);

        binding.rvReviews.setAdapter(reviewAdapter);

    }

    private void setError(ErrorResponse error) {
        new AlertDialog.Builder(this)
                .setMessage(error.getStatusMessage())
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
