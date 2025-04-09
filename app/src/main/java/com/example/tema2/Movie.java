package com.example.tema2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "allMovies")
public class Movie implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    @TypeConverters(DateConverter.class)
    private Date releaseDate;
    private String genre;
    private String streamingPlatform;

    public Movie(String title, Date releaseDate, String genre, String streamingPlatform) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.streamingPlatform = streamingPlatform;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStreamingPlatform() {
        return streamingPlatform;
    }

    public void setStreamingPlatform(String streamingPlatform) {
        this.streamingPlatform = streamingPlatform;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre='" + genre + '\'' +
                ", streamingPlatform='" + streamingPlatform + '\'' +
                '}';
    }
}
