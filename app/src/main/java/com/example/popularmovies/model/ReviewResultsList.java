package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewResultsList {
    @SerializedName("results")
    private ArrayList<Review> reviewList;

    public ArrayList<Review> getReviewList(){
        return reviewList;
    }
}
