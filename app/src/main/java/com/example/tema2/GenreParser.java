package com.example.tema2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GenreParser {
    private static final String GENRETITLE = "genreTitle";
    private static final String DESCRIPTION = "description";
    private static final String NUMBEROFMOVIES = "numberOfMovies";

    public static List<Genre> parsareJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareGenres(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Genre> parsareGenres(JSONArray jsonArray) throws JSONException {
        List<Genre> genres = new ArrayList<>();
        for (int i=0; i< jsonArray.length(); i++){
            genres.add(parsareGenre(jsonArray.getJSONObject(i)));
        }

        return genres;
    }
    private static Genre parsareGenre(JSONObject jsonObject) throws JSONException {
        String genreTitle = jsonObject.getString(GENRETITLE);
        String description = jsonObject.getString(DESCRIPTION);
        int numberOfMovies = jsonObject.getInt(NUMBEROFMOVIES);

        return new Genre(genreTitle, description, numberOfMovies);
    }
}
