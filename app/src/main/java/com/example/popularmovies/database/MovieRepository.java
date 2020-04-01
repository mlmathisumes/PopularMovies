package com.example.popularmovies.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies.GetMovieDetailDataSource;
import com.example.popularmovies.GetMoviesDataService;
import com.example.popularmovies.GetMovieDataSource;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.utils.AppExecutors;
import com.example.popularmovies.utils.Constants;

import java.util.List;

public class MovieRepository {
    private static MovieRepository mInstance;
    private static final Object LOCK = new Object();
    private MutableLiveData<List<Movie>> movieListLiveDataCache;
    private MutableLiveData<Boolean> favoriteIcon;
    private Application application;
    private AppDatabase appDatabase;
    private final GetMovieDataSource getMovieDataSource;
    private final GetMovieDetailDataSource getMovieDetailDataSource;
    private AppExecutors appExecutors;
    private GetMoviesDataService getMoviesDataService;
    private static final String TAG = MovieRepository.class.getSimpleName();

    // Constructor
    private MovieRepository(GetMovieDataSource getMovieDataSource, AppDatabase appDatabase, AppExecutors appExecutors, GetMoviesDataService getMoviesDataService, GetMovieDetailDataSource getMovieDetailDataSource) {
        this.getMovieDataSource = getMovieDataSource;
        this.appDatabase = appDatabase;
        this.appExecutors = appExecutors;
        this.getMoviesDataService = getMoviesDataService;
        this.getMovieDetailDataSource = getMovieDetailDataSource;
        favoriteIcon = new MutableLiveData<>();
    }

    public synchronized static MovieRepository
        getInstance(Context context, GetMovieDataSource getMovieDataSource,
                    AppDatabase appDatabase, AppExecutors appExecutors, GetMoviesDataService getMoviesDataService,
                    GetMovieDetailDataSource getMovieDetailDataSource){
        if(mInstance == null){
            synchronized (LOCK){
                mInstance = new MovieRepository(getMovieDataSource, appDatabase, appExecutors, getMoviesDataService, getMovieDetailDataSource);
                Log.d(TAG, "Made new Repository");
            }
        }
        return mInstance;
    }

    public LiveData<List<Movie>> getMovieListLiveData(String sort_order){
        if(sort_order.equals("favorites")){
            return getMoviesFromDb();
        }
        return getMovieDataSource.getDownloadedMovies(sort_order);
    }

    public LiveData<Movie> getMovieById(int id, String sort_order){
        if(sort_order.equals(Constants.FAVORITES)){
            return getMovieFromCacheDb(id);
        }
        return getMovieDataSource.getMovieById(id);
    }

    public LiveData<List<Review>> getReviews(int id){
        return getMovieDetailDataSource.getReview(id);
    }

    public LiveData<List<Trailer>> getTrailers(int id){
        return getMovieDetailDataSource.getTrailers(id);
    }

    public void initMoviesDb(){
        if(movieListLiveDataCache == null){
            movieListLiveDataCache = new MutableLiveData<>();
            appExecutors.diskIO().execute(new Runnable() {

                @Override
                public void run() {
                    List<Movie> dbMovies = appDatabase.movieDao().loadMovie();
                    if(dbMovies != null){
                        movieListLiveDataCache.postValue(dbMovies);
                        Log.d(TAG, "Load from Database");
                    }
                }
            });
        }
        Log.d(TAG, "Load from Cache");
    }

    private MutableLiveData<List<Movie>> getMoviesFromDb(){

        return movieListLiveDataCache;
    }

    private MutableLiveData<Movie> getMovieFromCacheDb(int id){
        MutableLiveData<Movie> movieMutableLiveDataCacheDb = new MutableLiveData();
        List<Movie> moviesFromCache = movieListLiveDataCache.getValue();
        for(Movie movie: moviesFromCache){
            if(movie.getId() == id){
                movieMutableLiveDataCacheDb.setValue(movie);
            }
        }
        return movieMutableLiveDataCacheDb;
    }


    public MutableLiveData<Boolean> checkIfMovieInDb(final int id){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Movie movie = appDatabase.movieDao().loadMovieById(id);
                if(movie != null){
                    Log.d("MovieViewModel", "Movie Found");
                    favoriteIcon.postValue(true);
                }else{
                    Log.d("MovieViewModel", "No movie found in DB");
                    favoriteIcon.postValue(false);
                }
            }
        });
        return favoriteIcon;
    }

    public void deleteMovie(final Movie movie){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().deleteMovie(movie);
                Log.d(TAG, "Movie deleted");
                List<Movie> dbMovies = appDatabase.movieDao().loadMovie();

                if(dbMovies != null) {
                    movieListLiveDataCache.postValue(dbMovies);
                    Log.d(TAG, "Refreshed cache data");
                    favoriteIcon.postValue(false);

                }else{
                    movieListLiveDataCache.postValue(null);
                }
            }
        });
    }

    public void insertMovie(final Movie movie){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().insertMovie(movie);
                Log.d(TAG, "Movie added");
                List<Movie> dbMovies = appDatabase.movieDao().loadMovie();

                if(dbMovies != null) {
                    movieListLiveDataCache.postValue(dbMovies);
                    Log.d(TAG, "Refreshed cache data");
                    favoriteIcon.postValue(true);

                }else{
                    movieListLiveDataCache.postValue(null);
                }
            }
        });
    }

}
