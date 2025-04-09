package com.example.tema2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

enum Enjoy {Yes, No}
@Entity(tableName = "historyAll")
public class MovieInHistory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String titleHistory;
    @TypeConverters(DateConverter.class)
    private Date releaseDateHistory;
    private String genreHistory;
    private Enjoy enjoy;

    public MovieInHistory(String titleHistory, Date releaseDateHistory, String genreHistory, Enjoy enjoy) {
        this.titleHistory = titleHistory;
        this.releaseDateHistory = releaseDateHistory;
        this.genreHistory = genreHistory;
        this.enjoy = enjoy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleHistory() {
        return titleHistory;
    }

    public void setTitleHistory(String titleHistory) {
        this.titleHistory = titleHistory;
    }

    public Date getReleaseDateHistory() {
        return releaseDateHistory;
    }

    public void setReleaseDateHistory(Date releaseDateHistory) {
        this.releaseDateHistory = releaseDateHistory;
    }

    public String getGenreHistory() {
        return genreHistory;
    }

    public void setGenreHistory(String genreHistory) {
        this.genreHistory = genreHistory;
    }

    public Enjoy getEnjoy() {
        return enjoy;
    }

    public void setEnjoy(Enjoy enjoy) {
        this.enjoy = enjoy;
    }

    @Override
    public String toString() {
        return "MovieInHistory{" +
                "id=" + id +
                ", titleHistory='" + titleHistory + '\'' +
                ", releaseDateHistory=" + releaseDateHistory +
                ", genreHistory='" + genreHistory + '\'' +
                ", enjoy=" + enjoy +
                '}';
    }
}
