package com.example.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.database.MovieRepository;
import com.example.popularmovies.viewmodel.DetailViewModel;


public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application application;
    private final MovieRepository movieRepository;

    public DetailViewModelFactory(Application application, MovieRepository movieRepository){
        this.application = application;
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(application, movieRepository);
    }
}
