package com.example.popularmovies.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.GetMovieDataService;
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
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = (TextView) findViewById(R.id.helloWorld);

        GetMovieDataService jsonMovieDBApi = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);

        String parameter = "f4ea32f9b797782899f3f11f86e81007";

        Call<ResultsList> call = jsonMovieDBApi.getMovies(parameter);

        call.enqueue(new Callback<ResultsList>() {
            @Override
            public void onResponse(Call<ResultsList> call, Response<ResultsList> response) {
                Log.d(TAG, "Getting mvovies");
                ArrayList<Movie> movieArrayList = response.body().getMovieList();
                Movie movie = movieArrayList.get(0);
                textViewResult.setText(movie.getTitle());
            }

            @Override
            public void onFailure(Call<ResultsList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong.. Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

}
