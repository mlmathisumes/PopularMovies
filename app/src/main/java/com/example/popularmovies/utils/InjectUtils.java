package com.example.popularmovies.utils;

import android.app.Application;
import android.content.Context;

import com.example.popularmovies.viewmodel.DetailViewModelFactory;
import com.example.popularmovies.GetMovieDetailDataSource;
import com.example.popularmovies.GetMoviesDataService;
import com.example.popularmovies.GetMovieDataSource;
import com.example.popularmovies.viewmodel.MainViewModelFactory;
import com.example.popularmovies.database.AppDatabase;
import com.example.popularmovies.database.MovieRepository;

public class InjectUtils {

    private static MovieRepository provideRepository(Context context){
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        AppExecutors appExecutors = AppExecutors.getInstance();
        GetMoviesDataService getMoviesDataService = RetrofitInstance.getRetrofitInstance().create(GetMoviesDataService.class);
        GetMovieDataSource getMovieDataSource = GetMovieDataSource.getInstance(getMoviesDataService);
        GetMovieDetailDataSource getMovieDetailDataSource = GetMovieDetailDataSource.getInstance(getMoviesDataService);
        return MovieRepository.getInstance(context.getApplicationContext(),getMovieDataSource, appDatabase, appExecutors, getMoviesDataService, getMovieDetailDataSource);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Application application){
        MovieRepository movieRepository = provideRepository(application.getApplicationContext());
        return new MainViewModelFactory(application, movieRepository);
    }

    public static DetailViewModelFactory provideDetailViewModelFactory(Application application){
        MovieRepository movieRepository = provideRepository(application.getApplicationContext());
        return new DetailViewModelFactory(application, movieRepository);
    }
}
