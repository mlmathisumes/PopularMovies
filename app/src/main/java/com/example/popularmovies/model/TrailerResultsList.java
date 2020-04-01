package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailerResultsList {
    @SerializedName("results")
    private ArrayList<Trailer> trailerList;

    public ArrayList<Trailer> getTrailerList(){
        return trailerList;
    }


}
