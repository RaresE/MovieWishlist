package com.example.tema2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieInHistory.class}, version = 1, exportSchema = false)
public abstract class MovieInHistoryDB extends RoomDatabase {
    private static MovieInHistoryDB instance;
    private static final String dbName = "historyAll.db";
    public static MovieInHistoryDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, MovieInHistoryDB.class, dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract MovieInHistoryDAO getMovieInHistoryDAO();
}
