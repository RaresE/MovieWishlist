package com.example.tema2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieParser{
    private static final String TITLE = "title";
    private static final String RELEASEDATE = "releaseDate";
    private static final String GENRE = "genre";
    private static final String STREAMINGPLATFORM = "streamingPlatform";

    public static List<Movie> parsareJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareMovies(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Movie> parsareMovies(JSONArray jsonArray) throws JSONException, ParseException {
        List<Movie> movies = new ArrayList<>();
        for(int i=0; i<jsonArray.length();i++){
            movies.add(parsareMovie(jsonArray.getJSONObject(i)));
        }

        return movies;
    }

    private static Movie parsareMovie(JSONObject jsonObject) throws JSONException, ParseException {
        String title = jsonObject.getString(TITLE);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String releaseDateString = jsonObject.getString(RELEASEDATE);
        Date releaseDate = sdf.parse(releaseDateString);

        String genre = jsonObject.getString(GENRE);
        String streamingPlatform = jsonObject.getString(STREAMINGPLATFORM);

        return new Movie(title, releaseDate, genre, streamingPlatform);
    }
}
