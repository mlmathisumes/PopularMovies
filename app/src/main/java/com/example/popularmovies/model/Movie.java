package com.example.popularmovies.model;



import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import androidx.room.PrimaryKey;


@Entity(tableName = "favorite")
public class Movie implements Parcelable {

    @PrimaryKey
    private int id;
    private double popularity;
    private int vote_count;
    private String poster_path;
    private String backdrop_path;
    private String original_title;
    private String title;
    private double vote_average;
    private String overview;
    private String release_date;
    private transient boolean favorite = false;

    protected Movie(Parcel in) {
        id = in.readInt();
        popularity = in.readDouble();
        vote_count = in.readInt();
        poster_path = in.readString();
        backdrop_path = in.readString();
        original_title = in.readString();
        title = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        release_date = in.readString();
        favorite = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Movie(int id, double popularity, int vote_count, String poster_path, String backdrop_path, String original_title, String title, double vote_average, String overview, String release_date) {
        this.id = id;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.original_title = original_title;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeDouble(popularity);
        parcel.writeInt(vote_count);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(original_title);
        parcel.writeString(title);
        parcel.writeDouble(vote_average);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }
}
