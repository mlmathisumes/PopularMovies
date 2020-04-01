package com.example.popularmovies.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.popularmovies.database.MovieRepository;
import com.example.popularmovies.model.Movie;

import java.util.List;

public class MovieListViewModel extends AndroidViewModel {

    private static final String TAG = MovieListViewModel.class.getSimpleName();

    private final MovieRepository movieRepository;
    private final MediatorLiveData<List<Movie>> movieListLiveData;
    private LiveData<List<Movie>> movieListSource;


    public MovieListViewModel(Application application, MovieRepository movieRepository) {
        super(application);
        this.movieRepository = movieRepository;
        movieListLiveData = new MediatorLiveData<>();
        Log.d(TAG, "Create MovieListViewModel Instance");
    }

    public LiveData<List<Movie>> getMovies(){
        return movieListLiveData;
    }

    /**
     * This method fetches the movie listing and caches database data
     *
     * @param sort_order The selected sort order from the RecyclerView
     */
    public void init(String sort_order){
        fetchMovieList(sort_order);
        movieRepository.initMoviesDb();
    }

    /**
     * This method fetches the movie listing
     *
     * @param sortOrder The selected sort order from the RecyclerView
     */
    public void fetchMovieList(String sortOrder){
        final LiveData<List<Movie>> movieList;
        movieList = movieRepository.getMovieListLiveData(sortOrder);

        if(sortOrder.equals("favorites") && movieList == null){
            movieListLiveData.setValue(null);
        }
        if(movieListSource != null){
            movieListLiveData.removeSource(movieListSource);
            Log.d(TAG, "Removing source");
        }
        movieListSource = movieList;
        movieListLiveData.addSource(movieListSource, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieListLiveData.postValue(movies);
            }
        });

    }


}


