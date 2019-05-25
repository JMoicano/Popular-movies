package br.com.jmoicano.popularmovies.main.view.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.jmoicano.popularmovies.R;
import br.com.jmoicano.popularmovies.databinding.ActivityMainBinding;
import br.com.jmoicano.popularmovies.details.view.ui.DetailsActivity;
import br.com.jmoicano.popularmovies.main.view.adapter.MovieListAdapter;
import br.com.jmoicano.popularmovies.main.viewmodel.MainActivityViewModel;
import br.com.jmoicano.popularmovies.main.viewmodel.MainActivityViewModelFactory;
import br.com.jmoicano.popularmovies.services.model.ErrorResponse;
import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;
import br.com.jmoicano.popularmovies.services.moviesservices.source.MovieRepository;
import br.com.jmoicano.popularmovies.services.moviesservices.source.local.MovieLocalDataSource;
import br.com.jmoicano.popularmovies.services.moviesservices.source.remote.MovieRemoteDataSource;
import br.com.jmoicano.popularmovies.util.ViewUtils;

import static br.com.jmoicano.popularmovies.services.Constants.FAVORITE;
import static br.com.jmoicano.popularmovies.services.Constants.MOVIE_EXTRA;
import static br.com.jmoicano.popularmovies.services.Constants.POPULARITY;
import static br.com.jmoicano.popularmovies.services.Constants.RATE;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainActivityViewModel viewModel;

    private MovieListAdapter adapter;

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                new MainActivityViewModelFactory(
                        MovieRepository.getInstance(
                                MovieRemoteDataSource.getInstance(),
                                MovieLocalDataSource.getInstance(this)
                        )
                )).get(MainActivityViewModel.class);
    }

    private void setupObservers() {
        Observer<String> sortObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                viewModel.getMovies(s);
            }
        };
        Observer<Resource<MovieDiscoverResponseModel>> discoverObserver = new Observer<Resource<MovieDiscoverResponseModel>>() {
            @Override
            public void onChanged(Resource<MovieDiscoverResponseModel> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        setLoading(false);
                        if (resource.data != null) {
                            viewModel.updateMovies(resource.data.getResults());
                            adapter.update();
                            binding.rvMovies.setVisibility(View.VISIBLE);
                        }
                        break;
                    case ERROR:
                        setLoading(false);
                        if (resource.errorResponse != null) {
                            ErrorResponse errorResponse = resource.errorResponse;
                            setError(errorResponse);
                        }
                        break;
                    case LOADING:
                        setLoading(true);
                        break;
                }
            }
        };
        viewModel.getSort().observe(this, sortObserver);
        viewModel.getDiscoverData().observe(this, discoverObserver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        setupObservers();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        int spanCount = ViewUtils.calculateNoOfColumns(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount, RecyclerView.VERTICAL, false);
        binding.rvMovies.setLayoutManager(layoutManager);
        adapter = new MovieListAdapter(viewModel) {
            @Override
            public void onMovieClick(MovieResultModel movie) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(MOVIE_EXTRA, movie);
                startActivity(intent);
            }
        };
        binding.rvMovies.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_popularity:
                viewModel.setSort(POPULARITY);
                return true;
            case R.id.action_rated:
                viewModel.setSort(RATE);
                return true;
            case R.id.action_favorite:
                viewModel.setSort(FAVORITE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setLoading(boolean state) {
        if (state) {
            binding.rvMovies.setVisibility(View.GONE);
            binding.pbLoadging.setVisibility(View.VISIBLE);
        } else {
            binding.pbLoadging.setVisibility(View.GONE);
        }
    }

    private void setError(ErrorResponse error) {
        new AlertDialog.Builder(this)
                .setMessage(error.getStatusMessage())
                .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.setSort(POPULARITY);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
