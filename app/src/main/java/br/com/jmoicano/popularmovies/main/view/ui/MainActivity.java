package br.com.jmoicano.popularmovies.main.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.jmoicano.popularmovies.R;
import br.com.jmoicano.popularmovies.databinding.ActivityMainBinding;
import br.com.jmoicano.popularmovies.details.view.ui.DetailsActivity;
import br.com.jmoicano.popularmovies.main.view.adapter.MovieListAdapter;
import br.com.jmoicano.popularmovies.main.viewmodel.MainActivityViewModel;
import br.com.jmoicano.popularmovies.services.model.ErrorResponse;
import br.com.jmoicano.popularmovies.services.model.Resource;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieDiscoverResponseModel;
import br.com.jmoicano.popularmovies.services.moviesmodels.MovieResultModel;

import static br.com.jmoicano.popularmovies.services.Constants.MOVIE_EXTRA;
import static br.com.jmoicano.popularmovies.services.Constants.POPULARITY;
import static br.com.jmoicano.popularmovies.services.Constants.RATE;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainActivityViewModel viewModel;

    private MovieListAdapter adapter;

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
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
                switch (resource.status){
                    case SUCCESS:
                        if(resource.data != null){
                            viewModel.updateMovies(resource.data.getResults());
                            adapter.update();
                        }
                        break;
                    case ERROR:
                        if(resource.errorResponse != null){
                            ErrorResponse errorResponse = resource.errorResponse;
                        }
                        break;
                    case LOADING:
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
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
