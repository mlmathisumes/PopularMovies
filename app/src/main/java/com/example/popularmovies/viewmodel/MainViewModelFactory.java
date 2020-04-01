package com.example.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.database.MovieRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRepository movieRepository;
    private final Application application;

    public MainViewModelFactory(Application application, MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieListViewModel(application, movieRepository);
    }
}
