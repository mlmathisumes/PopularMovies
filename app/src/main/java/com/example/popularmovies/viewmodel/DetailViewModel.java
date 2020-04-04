package com.example.popularmovies.viewmodel;

import android.app.Application;
import android.util.Log;


import androidx.annotation.NonNull;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


import com.example.popularmovies.database.AppDatabase;
import com.example.popularmovies.database.MovieRepository;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private final MovieRepository movieRepository;
    private LiveData<List<Trailer>> trailerListLiveData;
    private LiveData<List<Review>> reviewListLiveData;
    private LiveData<Movie> movieLiveData;
    private LiveData<Boolean> favorite;
    private AppDatabase appDatabase;
    private MutableLiveData<Boolean> isFavorite;
    public static final String TAG = AndroidViewModel.class.getSimpleName();

    public DetailViewModel(@NonNull Application application, MovieRepository movieRepository) {
        super(application);
        this.movieRepository = movieRepository;
        appDatabase = AppDatabase.getInstance(application);
        isFavorite = new MutableLiveData<>();
    }

    public LiveData<List<Trailer>> getTrailerListLiveData() {
        return trailerListLiveData;
    }

    public LiveData<List<Review>> getReviewListLiveData() {
        return reviewListLiveData;
    }

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public LiveData<Boolean> getFavorite(){
        return favorite;
    }

    /**
     * This method adds the selected movie to your favorite list
     *
     * @param id The selected movies id value
     * @param sort_order The sort order from the RecyclerView
     */
    public void loadMovie(final int id, String sort_order) {
        // Load movie from Cache
        movieLiveData = movieRepository.getMovieById(id, sort_order);

        // Check if movie exist in database and set Favorite icon
        LiveData<List<Movie>> movieList = movieRepository.getMoviesFromDb();
        favorite = Transformations.map(movieList, new Function<List<Movie>, Boolean>() {
            @Override
            public Boolean apply(List<Movie> input) {
                for(Movie movie: input){
                    if(movie.getId() == id){
                        return true;
                    }
                }

                return false;
            }
        });

        // Load movie trailer data
        trailerListLiveData = movieRepository.getTrailers(id);

        // Load movie review data
        reviewListLiveData = movieRepository.getReviews(id);
    }

    /**
     * This method deletes the selected movie to your favorite list
     *
     * @param movie The selected movie from the RecyclerView
     */
    public void deleteMovie(Movie movie){
        isFavorite.setValue(false);
        favorite = isFavorite;
        movieRepository.deleteMovie(movie);
    }

    /**
     * This method adds the selected movie to your favorite list
     *
     * @param movie The selected movie from the RecyclerView
     */
    public void insertMovie(Movie movie){
        isFavorite.setValue(true);
        favorite = isFavorite;
        movieRepository.insertMovie(movie);
    }

 /*   private String downloadMovieImage(String poster_path) {
        poster_path = "https://image.tmdb.org/t/p/w342" + poster_path;
        Glide.with(getApplication())
                .asBitmap()
                .load(poster_path)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }*/


}
