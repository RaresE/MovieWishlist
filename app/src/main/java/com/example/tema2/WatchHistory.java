package com.example.tema2;

import java.util.ArrayList;
import java.util.List;

public class WatchHistory {
    private User user;
    private List<Movie> watchedMovies;

    public WatchHistory(User user) {
        this.user = user;
        this.watchedMovies = new ArrayList<>();
    }

    public void addMovieHistory(Movie movie){
        watchedMovies.add(movie);
    }

    public User getUser() {
        return user;
    }

    public List<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "WatchHistory{" +
                "user=" + user +
                ", watchedMovies=" + watchedMovies +
                '}';
    }
}
