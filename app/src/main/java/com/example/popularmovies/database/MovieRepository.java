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
    private MutableLiveData<Boolean> favoriteIcon;
    private AppDatabase appDatabase;
    private final GetMovieDataSource getMovieDataSource;
    private final GetMovieDetailDataSource getMovieDetailDataSource;
    private AppExecutors appExecutors;
    private static final String TAG = MovieRepository.class.getSimpleName();

    // Constructor
    private MovieRepository(GetMovieDataSource getMovieDataSource, AppDatabase appDatabase, AppExecutors appExecutors, GetMovieDetailDataSource getMovieDetailDataSource) {
        this.getMovieDataSource = getMovieDataSource;
        this.appDatabase = appDatabase;
        this.appExecutors = appExecutors;
        this.getMovieDetailDataSource = getMovieDetailDataSource;
        favoriteIcon = new MutableLiveData<>();
    }

    public synchronized static MovieRepository
        getInstance(Context context, GetMovieDataSource getMovieDataSource,
                    AppDatabase appDatabase, AppExecutors appExecutors,
                    GetMovieDetailDataSource getMovieDetailDataSource){
        if(mInstance == null){
            synchronized (LOCK){
                mInstance = new MovieRepository(getMovieDataSource, appDatabase, appExecutors, getMovieDetailDataSource);
                Log.d(TAG, "Made new Repository");
            }
        }
        return mInstance;
    }

    public LiveData<List<Movie>> getMovieListLiveData(String sort_order){
        return getMovieDataSource.getDownloadedMovies(sort_order);
    }

    public LiveData<Movie> getMovieById(int id, String sort_order){
        if(sort_order.equals(Constants.FAVORITES)){
            return getMovieFromDb(id);
        }
        return getMovieDataSource.getMovieById(id);
    }

    public LiveData<List<Review>> getReviews(int id){
        return getMovieDetailDataSource.getReview(id);
    }

    public LiveData<List<Trailer>> getTrailers(int id){
        return getMovieDetailDataSource.getTrailers(id);
    }

    public LiveData<List<Movie>> getMoviesFromDb(){
        return appDatabase.movieDao().loadMovie();
    }

    public LiveData<Movie> getMovieFromDb(int id){
        return appDatabase.movieDao().loadMovieById(id);
    }

/*    public MutableLiveData<Boolean> checkIfMovieInDb(final int id){
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
    }*/

    public void deleteMovie(final Movie movie){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().deleteMovie(movie);
                Log.d(TAG, "Movie deleted");
                favoriteIcon.postValue(false);
            }
        });
    }

    public void insertMovie(final Movie movie){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().insertMovie(movie);
                Log.d(TAG, "Movie added");
                favoriteIcon.postValue(true);

            }
        });
    }

}
