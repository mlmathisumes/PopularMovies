package com.example.popularmovies.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultsList {

    @SerializedName("results")
    private ArrayList<Movie> movieList;

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }



}
