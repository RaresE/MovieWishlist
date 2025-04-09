package com.example.tema2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void insertMovie(Movie movie);

    @Query("SELECT * FROM allMovies")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM allMovies WHERE id=:id")
    Movie getMovieById(int id);

    @Query("SELECT * FROM allMovies WHERE genre=:genre")
    List<Movie> getMoviesByGenre(String genre);

    @Query("SELECT * FROM allMovies WHERE title=:title")
    Movie getMovieByTitle(String title);

    @Delete
    void deleteMovie(Movie movie);
}
