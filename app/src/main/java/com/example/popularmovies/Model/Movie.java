package com.example.popularmovies.Model;

public class Movie {

    private double popularity;
    private int vote_count;
    private boolean video;
    private String poster_path;
    private boolean adult;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private int[] genre_ids;
    private String title;
    private double vote_average;
    private String text;
    private String release_date;
    public double getPopularity() {
        return popularity;
    }
    public int getVote_count() {
        return vote_count;
    }
    public boolean isVideo() {
        return video;
    }
    public String getPoster_path() {
        return poster_path;
    }
    public boolean isAdult() {
        return adult;
    }
    public String getBackdrop_path() {
        return backdrop_path;
    }
    public String getOriginal_language() {
        return original_language;
    }
    public String getOriginal_title() {
        return original_title;
    }
    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getText() {
        return text;
    }

    public String getRelease_date() {
        return release_date;
    }
}
