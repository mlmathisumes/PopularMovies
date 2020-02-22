package com.example.popularmovies.viewmodel;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.GetMovieDataService;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.ResultsList;
import com.example.popularmovies.utils.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListViewModel extends ViewModel {

    private static final String TAG = MovieListViewModel.class.getSimpleName();
    private MutableLiveData<ArrayList<Movie>> movieLiveData;
    private ArrayList<Movie> movieArrayList;
    private GetMovieDataService jsonMovieDBApi;
    private static final String API_KEY = "f4ea32f9b797782899f3f11f86e81007";


    public MovieListViewModel(){
        movieLiveData = new MutableLiveData<>();

        init();
    }



    public MutableLiveData<ArrayList<Movie>> getMovieMutableLiveData(){
        if(movieLiveData == null){
            movieLiveData = new MutableLiveData<>();
        }
        return  movieLiveData;
    }

    public void init() {
        jsonMovieDBApi = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
        getPopularMovieList();


    }

    public void getPopularMovieList() {
        Call<ResultsList> call = jsonMovieDBApi.getPopularMovies(API_KEY);

        call.enqueue(new Callback<ResultsList>() {
            @Override
            public void onResponse(Call<ResultsList> call, Response<ResultsList> response) {
                movieArrayList = response.body().getPopularMovieList();
                if(movieArrayList != null){
                    movieLiveData.setValue(movieArrayList);
                    Log.d(TAG, "Live Data set");

                }else{
                    Log.d(TAG, "Empty Arraylist");
                }
            }

            @Override
            public void onFailure(Call<ResultsList> call, Throwable t) {
                Log.d(TAG, "Something went wrong.. Error message: " + t.getMessage());
            }
        });
    }

    public void getTopRatedMoviesList(){
        Call<ResultsList> call = jsonMovieDBApi.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<ResultsList>() {
            @Override
            public void onResponse(Call<ResultsList> call, Response<ResultsList> response) {
                movieArrayList = response.body().getTopRatedMoviesList();
                if(movieArrayList != null){
                    movieLiveData.setValue(movieArrayList);
                    Log.d(TAG, "Live Data set");

                }else{
                    Log.d(TAG, "Empty Arraylist");
                }
            }

            @Override
            public void onFailure(Call<ResultsList> call, Throwable t) {
                Log.d(TAG, "Something went wrong.. Error message: " + t.getMessage());
            }
        });
    }

}


