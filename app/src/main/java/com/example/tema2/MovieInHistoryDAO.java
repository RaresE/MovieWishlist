package com.example.tema2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieInHistoryDAO {

    @Insert
    void insertMovieInHistory(MovieInHistory movieInHistory);

    @Query("SELECT * FROM historyAll")
    List<MovieInHistory> getAllMoviesInHistory();

    @Query("SELECT * FROM historyAll WHERE id=:id")
    MovieInHistory getMovieInHistoryById(int id);

    @Query("SELECT * FROM historyAll WHERE genreHistory=:genreHistory")
    List<MovieInHistory> getMoviesInHistoryByGenre(String genreHistory);

    @Delete
    void deleteMovieInHistory(MovieInHistory movieInHistory);
}
