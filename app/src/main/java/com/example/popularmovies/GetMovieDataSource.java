package com.example.popularmovies;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResultsList;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMovieDataSource {

    private static final String TAG = GetMoviesDataService.class.getSimpleName();

    private final HashMap<String, MutableLiveData<List<Movie>>> movieCache;
    private MutableLiveData<List<Movie>> activeMutableMovieList;
    private MutableLiveData<Movie> activeMutableMovie;
    private static GetMovieDataSource sInstance;
    private static final Object LOCK = new Object();
    private GetMoviesDataService getMoviesDataService;
    private static final String API_KEY = "f4ea32f9b797782899f3f11f86e81007";

    private  GetMovieDataSource(GetMoviesDataService getMoviesDataService){

        this.getMoviesDataService = getMoviesDataService;
        activeMutableMovie = new MutableLiveData<>();
        activeMutableMovieList = new MutableLiveData<>();
        movieCache = new HashMap<>();
    }

    public synchronized static GetMovieDataSource getInstance(GetMoviesDataService getMoviesDataService){
        if(sInstance == null){
            sInstance = new GetMovieDataSource(getMoviesDataService);
            Log.d(TAG, "GetMovieDataSource instance");
        }
        return sInstance;
    }

    public MutableLiveData<List<Movie>> getDownloadedMovies(final String sort_by) {
        activeMutableMovieList = movieCache.get(sort_by);
        if(activeMutableMovieList != null){
            activeMutableMovieList = movieCache.get(sort_by);
        }else{
            Log.d(TAG, "Cache data not found");
            MutableLiveData newList = new MutableLiveData();
            fetchMovies(sort_by, newList);
            movieCache.put(sort_by, newList);
            activeMutableMovieList = newList;
            Log.d(TAG, "Cache data updated");
        }
        return activeMutableMovieList;

    }

    public MutableLiveData<Movie> getMovieById(int id){
       List<Movie> movieList = activeMutableMovieList.getValue();
       for(Movie movie: movieList){
           if(movie.getId() == id){
               activeMutableMovie.setValue(movie);
           }
       }
       return activeMutableMovie;
    }

    private void fetchMovies(final String sort_by, final MutableLiveData<List<Movie>> mutableLiveDataMovies){
        Log.d(TAG, "Fetching movie data");
            try{
                Call<MovieResultsList> call = getMoviesDataService.getMovies(sort_by , API_KEY);

                call.enqueue(new Callback<MovieResultsList>() {
                    @Override
                    public void onResponse(Call<MovieResultsList> call, Response<MovieResultsList> response) {
                        List<Movie> movies = response.body().getMovieList();
                        if(movies != null){
                            mutableLiveDataMovies.setValue(movies);

                        }else{
                            mutableLiveDataMovies.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResultsList> call, Throwable t) {
                        Log.d(TAG, "Something went wrong.. Error message: " + t.getMessage());
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }

    }
}
