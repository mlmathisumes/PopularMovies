package com.example.popularmovies;

import com.example.popularmovies.model.MovieResultsList;
import com.example.popularmovies.model.ReviewResultsList;
import com.example.popularmovies.model.TrailerResultsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMoviesDataService {

    @GET("movie/{sort_by}")
    Call<MovieResultsList> getMovies(@Path("sort_by") String sort_by,
                                            @Query("api_key") String apiKey
    );

    @GET("movie/{id}/videos")
    Call<TrailerResultsList> getTrailers(@Path("id") Integer id,
                                         @Query("api_key") String apiKey
    );

    @GET("movie/{id}/reviews")
    Call<ReviewResultsList> getReviews(@Path("id") Integer id,
                                       @Query("api_key") String apiKey
    );
}
