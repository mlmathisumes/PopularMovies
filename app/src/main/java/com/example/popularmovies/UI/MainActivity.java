package com.example.popularmovies.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.popularmovies.GetMovieDataService;
import com.example.popularmovies.MovieRecyclerViewAdapter;
import com.example.popularmovies.R;
import com.example.popularmovies.Utils.RetrofitInstance;
import com.example.popularmovies.Model.Movie;
import com.example.popularmovies.Model.ResultsList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter adapter;
    private int numberOfColumns = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolBar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        getPopularMovies();

    }

    private void initRecyclerView(ArrayList<Movie> movieArrayList) {
        recyclerView = findViewById(R.id.rv_Movies);

        adapter = new MovieRecyclerViewAdapter(this, movieArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setAdapter(adapter);

    }
    private void getPopularMovies(){
        GetMovieDataService jsonMovieDBApi = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);

        String parameter = "f4ea32f9b797782899f3f11f86e81007";

        Call<ResultsList> call = jsonMovieDBApi.getMovies(parameter);

        call.enqueue(new Callback<ResultsList>() {
            @Override
            public void onResponse(Call<ResultsList> call, Response<ResultsList> response) {
                ArrayList<Movie> movieArrayList = response.body().getMovieList();
                initRecyclerView(movieArrayList);
            }

            @Override
            public void onFailure(Call<ResultsList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong.. Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }


}
