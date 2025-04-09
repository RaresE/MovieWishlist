package com.example.tema2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDB extends RoomDatabase{
    private static MovieDB instance;
    private static final String dbName = "allMovies.db";
    public static MovieDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, MovieDB.class, dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract MovieDAO getMovieDAO();
}
