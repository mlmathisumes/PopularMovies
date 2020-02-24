package com.example.popularmovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.popularmovies.viewmodel.MovieListViewModel;
import com.example.popularmovies.adapter.MovieRecyclerViewAdapter;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private MainActivity context;
    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter adapter;
    private final int NUMBER_OF_COLUMNS = 3;
    private MovieListViewModel viewModel;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        recyclerView = findViewById(R.id.rv_Movies);
        context = this;


         // Initialize MovieList viewModel
        viewModel = ViewModelProviders.of(context).get(MovieListViewModel.class);
        viewModel.getMovieMutableLiveData().observe(context, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movieArrayList) {
                adapter = new MovieRecyclerViewAdapter(context, movieArrayList);
                recyclerView.setLayoutManager(new GridLayoutManager(context, NUMBER_OF_COLUMNS));
                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        this.menu = menu;
        menu.getItem(0).setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.popularity:
                viewModel.getPopularMovieList();
                hideSortOption("popularity");

                break;
            case R.id.rating:
                viewModel.getTopRatedMoviesList();
                hideSortOption("rating");
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
        if(sort.equals("rating")){
            menu.getItem(1).setEnabled(false);
            menu.getItem(0).setEnabled(true);
        }else{
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(true);
        }
    }
}
