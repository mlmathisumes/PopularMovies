package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

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

    protected Movie(Parcel in) {
        popularity = in.readDouble();
        vote_count = in.readInt();
        video = in.readByte() != 0;
        poster_path = in.readString();
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        genre_ids = in.createIntArray();
        title = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        release_date = in.readString();
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

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    private String overview;
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

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(popularity);
        parcel.writeInt(vote_count);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeString(poster_path);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(backdrop_path);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
        parcel.writeIntArray(genre_ids);
        parcel.writeString(title);
        parcel.writeDouble(vote_average);
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }
}
