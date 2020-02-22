package com.example.popularmovies;

import com.example.popularmovies.model.ResultsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovieDataService {

    @GET("popular")
    Call<ResultsList> getPopularMovies(
            @Query("api_key") String apiKey
    );

    @GET("top_rated")
    Call<ResultsList> getTopRatedMovies(
            @Query("api_key") String apiKey
    );
}
