package com.example.tema2;

public class Genre {
    private String genreTitle;
    private String description;
    private int numberOfMovies;

    public Genre(String genreTitle, String description, int numberOfMovies) {
        this.genreTitle = genreTitle;
        this.description = description;
        this.numberOfMovies = numberOfMovies;
    }

    public int getNumberOfMovies() {
        return numberOfMovies;
    }

    public void setNumberOfMovies(int numberOfMovies) {
        this.numberOfMovies = numberOfMovies;
    }

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreTitle='" + genreTitle + '\'' +
                ", description='" + description + '\'' +
                ", numberOfMovies=" + numberOfMovies +
                '}';
    }
}
