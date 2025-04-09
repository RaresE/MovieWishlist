package com.example.tema2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieInHistoryParser {

    private static final String TITLEHISTORY = "titleHistory";
    private static final String RELEASEDATEHISTORY = "releaseDateHistory";
    private static final String  GENREHISTORY = "genreHistory";
    private static final String  ENJOY = "enjoy";

    public static List<MovieInHistory> parsareJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareMoviesInHistory(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    private static List<MovieInHistory> parsareMoviesInHistory(JSONArray jsonArray) throws JSONException, ParseException {
        List<MovieInHistory> movieInHistoryList = new ArrayList<>();
        for(int i=0; i<jsonArray.length();i++){
            movieInHistoryList.add(parsareMovieInHistory(jsonArray.getJSONObject(i)));
        }

        return movieInHistoryList;
    }

    private static MovieInHistory parsareMovieInHistory(JSONObject jsonObject) throws JSONException, ParseException {
        String titlehistory = jsonObject.getString(TITLEHISTORY);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String releaseDateH = jsonObject.getString(RELEASEDATEHISTORY);
        Date releaseDateHistory = sdf.parse(releaseDateH);

        String genreHistory = jsonObject.getString(GENREHISTORY);

        String enjoyHistory = jsonObject.getString(ENJOY);
        Enjoy enjoy = Enjoy.valueOf(enjoyHistory);

        return new MovieInHistory(titlehistory, releaseDateHistory, genreHistory, enjoy);
    }
}
