package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class  ResultsList {

    @SerializedName("results")
    private ArrayList<Movie> movieList;

    public ArrayList<Movie> getPopularMovieList() {
        return movieList;
    }

    public ArrayList<Movie> getTopRatedMoviesList(){
        return movieList;
    }



}
