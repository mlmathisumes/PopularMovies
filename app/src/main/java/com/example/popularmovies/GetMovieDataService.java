package com.example.popularmovies;

import com.example.popularmovies.Model.ResultsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovieDataService {

    @GET("popular")
    Call<ResultsList> getMovies(
            @Query("api_key") String apiKey
    );
}
