package com.example.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.popularmovies.model.Movie;

import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite")
    List<Movie> loadMovie();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM favorite WHERE id = :id")
    Movie loadMovieById(int id);


}
