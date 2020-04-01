package com.example.popularmovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.viewmodel.MainViewModelFactory;
import com.example.popularmovies.utils.Constants;
import com.example.popularmovies.utils.InjectUtils;
import com.example.popularmovies.viewmodel.MovieListViewModel;
import com.example.popularmovies.adapter.MovieRecyclerViewAdapter;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LifecycleOwner, MovieRecyclerViewAdapter.MovieAdapterOnItemClick {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivity context;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final int NUMBER_OF_COLUMNS = 3;
    private MovieListViewModel viewModel;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private Menu menu;
    private TextView emptyListing;
    private String sortOrder;
    private MovieRecyclerViewAdapter.MovieAdapterOnItemClick mClickHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        mClickHandler = this;

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        recyclerView = findViewById(R.id.rv_Movies);
        emptyListing = findViewById(R.id.emptyList);
        progressBar = findViewById(R.id.pb_loading_indicator);

        /* setLayoutManager sets our RecyclerView to have a GridLayoutManager */
        recyclerView.setLayoutManager(new GridLayoutManager(context, NUMBER_OF_COLUMNS));
        context = this;

        // Initialize MainView viewModel
        MainViewModelFactory factory = InjectUtils.provideMainActivityViewModelFactory(getApplication());
        viewModel = ViewModelProviders.of(context, factory).get(MovieListViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(movies == null || movies.size() < 1 ){
                    showEmptyListingMessage();
                    Log.d(TAG, "Movie size = " + movies.size());

                }else{
                    showListing();
                    movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(context, mClickHandler, movies);
                    recyclerView.setAdapter(movieRecyclerViewAdapter);
                    Log.d(TAG, "Movie list updated");
                }
            }
        });
        viewModel.init(getResources().getString(R.string.default_sort_order));
        sortOrder = getResources().getString(R.string.default_sort_order);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        this.menu = menu;
        hideSortOption(getResources().getString(R.string.default_sort_order));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.popularity:
                viewModel.fetchMovieList(Constants.POPULARITY);
                hideSortOption(Constants.POPULARITY);
                sortOrder = Constants.POPULARITY;
                break;
            case R.id.rating:
                viewModel.fetchMovieList(Constants.TOP_RATED);
                hideSortOption(Constants.TOP_RATED);
                sortOrder = Constants.TOP_RATED;
                break;
            case R.id.favorites:
                hideSortOption(Constants.FAVORITES);
                viewModel.fetchMovieList(Constants.FAVORITES);
                sortOrder = Constants.FAVORITES;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * This method disables the menu option based on the selected sort listing
     *
     * @param sort The selected sort option (rating or popularity)
     */
    private void hideSortOption(String sort){
        if(sort.equals(Constants.TOP_RATED)){
            menu.getItem(1).setEnabled(false);
            menu.getItem(0).setEnabled(true);
            menu.getItem(2).setEnabled(true);
        }
        if(sort.equals(Constants.POPULARITY)){
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(true);
            menu.getItem(2).setEnabled(true);
        }
        if(sort.equals(Constants.FAVORITES)){
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(true);
            menu.getItem(2).setEnabled(false);
        }
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(getResources().getString(R.string.movie), movie);
        intent.putExtra(getResources().getString(R.string.sort_order), sortOrder);
        startActivity(intent);
    }

    // Display the 'No movies available' message
    private void showEmptyListingMessage(){
        emptyListing.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    // Display the listing of movies
    private void showListing(){
        emptyListing.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }
}
