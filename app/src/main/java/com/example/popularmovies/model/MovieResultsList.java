package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResultsList {

    @SerializedName("results")
    private ArrayList<Movie> movieList;

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }
}
