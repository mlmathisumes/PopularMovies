package com.example.popularmovies.model;

import android.os.Parcel;

public class RemoteMovie extends Movie {
    protected RemoteMovie(Parcel in) {
        super(in);
    }

    public RemoteMovie(int id, double popularity, int vote_count, String poster_path, String backdrop_path, String original_title, String title, double vote_average, String overview, String release_date) {
        super(id, popularity, vote_count, poster_path, backdrop_path, original_title, title, vote_average, overview, release_date);
    }
}
