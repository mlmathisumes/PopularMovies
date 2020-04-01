package com.example.popularmovies.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


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

    public DetailViewModel(@NonNull Application application, MovieRepository movieRepository) {
        super(application);
        this.movieRepository = movieRepository;
        appDatabase = AppDatabase.getInstance(application);
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
    public void loadMovie(int id, String sort_order) {
        // Load movie from Cache
        movieLiveData = movieRepository.getMovieById(id, sort_order);

        // Check if movie exist in database and set Favorite icon
        favorite = movieRepository.checkIfMovieInDb(id);

        // Load movie trailer data
        trailerListLiveData = movieRepository.getTrailers(id);

        // Load movie review data
        reviewListLiveData = movieRepository.getReviews(id);
    }

/*    public LiveData<List<Movie>> getMoviesFromDb() {
        return movieRepository.getMoviesFromDb();
    }*/

    /**
     * This method deletes the selected movie to your favorite list
     *
     * @param movie The selected movie from the RecyclerView
     */
    public void deleteMovie(Movie movie){
        movieRepository.deleteMovie(movie);

    }

    /**
     * This method adds the selected movie to your favorite list
     *
     * @param movie The selected movie from the RecyclerView
     */
    public void insertMovie(Movie movie){
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
